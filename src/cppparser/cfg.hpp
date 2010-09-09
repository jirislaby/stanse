#ifndef CPPPARSER_CFG_HPP
#define CPPPARSER_CFG_HPP

#include <vector>
#include <set>
#include <map>
#include <string>
#include <boost/assert.hpp>
#include <boost/variant.hpp>
#include <boost/none.hpp>
#include <boost/iterator/counting_iterator.hpp>
#include <boost/iterator/iterator_facade.hpp>

#include <memory>

class cfg
{
	struct graph_vertex;

public:
	enum node_type { nt_none, nt_exit, nt_value, nt_call, nt_phi };
	enum op_type { ot_none, ot_func, ot_oper, ot_const, ot_member, ot_node, ot_var, ot_varptr };

	typedef graph_vertex * vertex_descriptor;

	struct edge_descriptor
	{
		vertex_descriptor source;
		std::size_t parity;

		edge_descriptor(vertex_descriptor source, std::size_t parity)
			: source(source), parity(parity)
		{
		}

		friend bool operator==(edge_descriptor const & lhs, edge_descriptor const & rhs)
		{
			return lhs.source == rhs.source && lhs.parity == rhs.parity;
		}

		friend bool operator!=(edge_descriptor const & lhs, edge_descriptor const & rhs)
		{
			return !(lhs == rhs);
		}

		friend bool operator<(edge_descriptor const & lhs, edge_descriptor const & rhs)
		{
			return lhs.source < rhs.source || (lhs.source == rhs.source && lhs.parity < rhs.parity);
		}
	};

	typedef boost::variant<boost::none_t, std::string, vertex_descriptor> op_id;

	struct operand
	{
		op_type type;
		op_id id;

		operand(op_type type = ot_none, op_id id = boost::none)
			: type(type), id(id)
		{
		}
	};

	struct edge
	{
		std::size_t id;
		std::string cond;

		edge(std::size_t id = 0, std::string const & cond = std::string())
			: id(id), cond(cond)
		{
		}
	};

	struct node
	{
		node_type type;
		std::vector<operand> ops;
		void const * data;

		node(node_type type = nt_none, void * data = 0)
			: type(type), data(data)
		{
		}
	};

	cfg()
		: m_entry(0)
	{
	}

	cfg(cfg const & other)
		: m_entry(0), m_params(other.m_params), m_locals(other.m_locals)
	{
		std::map<vertex_descriptor, vertex_descriptor> isom;
		for (std::set<vertex_descriptor>::const_iterator it = other.m_vertices.begin(); it != other.m_vertices.end(); ++it)
		{
			isom[*it] = this->add_node(other[*it]);
		}

		if (other.entry() != 0)
			m_entry = isom.find(other.entry())->second;

		for (std::map<vertex_descriptor, vertex_descriptor>::const_iterator it = isom.begin(); it != isom.end(); ++it)
		{
			node & n = (*this)[it->second];
			for (std::size_t i = 0; i != n.ops.size(); ++i)
			{
				if (n.ops[i].id.which() == 2)
					n.ops[i].id = isom.find(boost::get<cfg::vertex_descriptor>(n.ops[i].id))->second;
			}

			std::pair<out_edge_iterator, out_edge_iterator> out_edge_range = out_edges(it->first, other);
			for (; out_edge_range.first != out_edge_range.second; ++out_edge_range.first)
			{
				edge_descriptor e = add_edge(it->second, isom.find(target(*out_edge_range.first, other))->second, *this).first;
				(*this)[e] = other[*out_edge_range.first];
			}
		}
	}

	~cfg()
	{
		for (std::set<vertex_descriptor>::iterator it = m_vertices.begin(); it != m_vertices.end(); ++it)
		{
			delete *it;
		}
	}

	cfg & operator=(cfg other)
	{
		swap(*this, other);
		return *this;
	}

	friend void swap(cfg & lhs, cfg & rhs)
	{
		using std::swap;
		lhs.m_vertices.swap(rhs.m_vertices);
		swap(lhs.m_entry, rhs.m_entry);
		lhs.m_params.swap(rhs.m_params);
		lhs.m_locals.swap(rhs.m_locals);
	}

	vertex_descriptor entry() const { return m_entry; }
	void entry(vertex_descriptor v) { m_entry = v; }

	vertex_descriptor add_node(node const & n)
	{
		std::auto_ptr<graph_vertex> res(new graph_vertex(n));
		m_vertices.insert(res.get());
		return res.release();
	}

	friend vertex_descriptor add_vertex(cfg & g)
	{
		std::auto_ptr<graph_vertex> res(new graph_vertex());
		g.m_vertices.insert(res.get());
		return res.release();
	}

	friend void clear_vertex(vertex_descriptor v, cfg & g)
	{
		// TODO: we assume here that the vertex has 
		// only a single predecessor, which has only one successor.
		for (std::size_t i = 0; i < v->succs.size(); ++i)
			v->succs[i].target->preds.erase(edge_descriptor(v, i));
		v->succs.clear();

		if (v->preds.empty())
			return;

		BOOST_ASSERT(v->preds.size() == 1);
		BOOST_ASSERT((*v->preds.begin()).source->succs.size() == 1);

		(*v->preds.begin()).source->succs.clear();
		v->preds.clear();
	}

	friend void remove_vertex(vertex_descriptor v, cfg & g)
	{
		BOOST_ASSERT(v->preds.empty());
		BOOST_ASSERT(v->succs.empty());
		if (g.m_entry == v)
			g.m_entry = 0;
		g.m_vertices.erase(v);
		delete v;
	}

	friend std::pair<edge_descriptor, bool> add_edge(vertex_descriptor source, vertex_descriptor target, cfg & g)
	{
		edge_descriptor new_edge(source, source->succs.size());

		target->preds.insert(new_edge);
		source->succs.push_back(target);
		return std::make_pair(new_edge, true);
	}

	void redirect_vertex(cfg::vertex_descriptor source, cfg::vertex_descriptor target)
	{
		// TODO: basic exception guarantees
		for (std::set<edge_descriptor>::const_iterator it = source->preds.begin(); it != source->preds.end(); ++it)
		{
			it->source->succs[it->parity].target = target;
			target->preds.insert(*it);
		}
		source->preds.clear();

		if (m_entry == source)
			m_entry = target;
	}

	friend vertex_descriptor source(edge_descriptor const & e, cfg const &)
	{
		return e.source;
	}

	friend vertex_descriptor target(edge_descriptor const & e, cfg const & g)
	{
		return e.source->succs[e.parity].target;
	}

	static vertex_descriptor null_vertex()
	{
		return 0;
	}

	typedef std::set<vertex_descriptor>::const_iterator vertex_iterator;
	friend std::pair<vertex_iterator, vertex_iterator> vertices(cfg const & g)
	{
		return std::pair<vertex_iterator, vertex_iterator>(g.m_vertices.begin(), g.m_vertices.end());
	};

	node & operator[](vertex_descriptor v) { return v->prop; }
	node const & operator[](vertex_descriptor v) const { return v->prop; }

	edge & operator[](edge_descriptor e) { return e.source->succs[e.parity].prop; }
	edge const & operator[](edge_descriptor e) const { return e.source->succs[e.parity].prop; }

	struct out_edge_iterator
		: boost::iterator_facade<out_edge_iterator, edge_descriptor const, boost::random_access_traversal_tag>
	{
		out_edge_iterator(vertex_descriptor source, std::size_t parity)
			: m_current(source, parity)
		{
		}

		edge_descriptor const & dereference() const { return m_current; };
		void increment() { ++m_current.parity; }
		void decrement() { --m_current.parity; }
		bool equal(out_edge_iterator const & other) const { return m_current == other.m_current; }
		void advance(std::size_t n) { m_current.parity += n; }
		std::ptrdiff_t distance_to(out_edge_iterator const & other) const { return other.m_current.parity - m_current.parity; }

	private:
		edge_descriptor m_current;
	};

	friend std::pair<out_edge_iterator, out_edge_iterator> out_edges(vertex_descriptor v, cfg const & g)
	{
		return std::make_pair(out_edge_iterator(v, 0), out_edge_iterator(v, v->succs.size()));
	}

	typedef std::set<edge_descriptor>::const_iterator in_edge_iterator;
	friend std::pair<in_edge_iterator, in_edge_iterator> in_edges(vertex_descriptor v, cfg const & g)
	{
		return std::make_pair(v->preds.begin(), v->preds.end());
	}

	typedef std::size_t degree_size_type;
	friend degree_size_type out_degree(vertex_descriptor v, cfg const &)
	{
		return v->succs.size();
	}

	friend degree_size_type in_degree(vertex_descriptor v, cfg const &)
	{
		return v->preds.size();
	}

	void add_param(std::string const & param)
	{
		m_params.push_back(param);
	}

	std::vector<std::string> const & params() const { return m_params; }

	void add_local(std::string const & param)
	{
		m_locals.push_back(param);
	}

	std::vector<std::string> const & locals() const { return m_locals; }

private:
	struct graph_edge
	{
		vertex_descriptor target;
		edge prop;

		graph_edge(vertex_descriptor target, edge prop = edge())
			: target(target), prop(prop)
		{
		}
	};

	struct graph_vertex
	{
		std::vector<graph_edge> succs;
		std::set<edge_descriptor> preds;
		node prop;

		graph_vertex(node prop = node())
			: prop(prop)
		{
		}
	};

	std::set<vertex_descriptor> m_vertices;
	vertex_descriptor m_entry;
	std::vector<std::string> m_params;
	std::vector<std::string> m_locals;
};

class program
{
public:
	program()
	{
		this->make_atom("");
	}

	std::size_t make_atom(std::string const & name)
	{
		std::map<std::string, std::size_t>::const_iterator it = atom_map.find(name);
		if (it == atom_map.end())
		{
			it = atom_map.insert(std::make_pair(name, atoms.size())).first;
			atoms.push_back(name);
		}

		return it->second;
	}

	void add_cfg(std::string const & name, cfg const & c)
	{
		m_cfgs.insert(std::make_pair(name, c));
	}

	std::map<std::string, cfg> const & cfgs() const { return m_cfgs; }
	std::map<std::string, cfg> & cfgs() { return m_cfgs; }

	void pretty_print(std::ostream & out) const;

private:
	std::vector<std::string> atoms;
	std::map<std::string, std::size_t> atom_map;

	std::map<std::string, cfg> m_cfgs;
};

#endif
