#ifndef CPPPARSER_CFG_HPP
#define CPPPARSER_CFG_HPP

#include <boost/assert.hpp>
#include <boost/variant.hpp>
#include <boost/none.hpp>
#include <boost/iterator/counting_iterator.hpp>
#include <boost/iterator/iterator_facade.hpp>
#include <boost/utility.hpp>

#include <algorithm>
#include <memory>
#include <vector>
#include <set>
#include <list>
#include <map>
#include <string>

struct range_tag
{
	std::size_t fname;
	int start_line, start_col;
	int end_line, end_col;

	friend bool operator==(range_tag const & lhs, range_tag const & rhs)
	{
		return lhs.fname == rhs.fname && lhs.start_line == rhs.start_line && lhs.start_col == rhs.start_col
			&& lhs.end_line == rhs.end_line && lhs.end_col == rhs.end_col;
	}

	friend bool operator!=(range_tag const & lhs, range_tag const & rhs)
	{
		return !(lhs == rhs);
	}
};

class filename_store
{
public:
	std::size_t add(std::string const & fname)
	{
		BOOST_ASSERT(m_fname_map.size() == m_fnames.size());
		std::pair<std::map<std::string, std::size_t>::iterator, bool> ins = m_fname_map.insert(std::make_pair(fname, m_fnames.size()));
		if (ins.second)
			m_fnames.push_back(fname);
		return ins.first->second;
	}

	std::string operator[](std::size_t idx) const
	{
		return m_fnames[idx];
	}

	std::size_t size() const
	{
		return m_fnames.size();
	}

private:
	std::map<std::string, std::size_t> m_fname_map;
	std::vector<std::string> m_fnames;
};

struct fullexpr_tag
{
	std::size_t id;

	friend bool operator==(fullexpr_tag const & lhs, fullexpr_tag const & rhs)
	{
		return lhs.id == rhs.id;
	}

	friend bool operator!=(fullexpr_tag const & lhs, fullexpr_tag const & rhs)
	{
		return !(lhs == rhs);
	}
};

class cfg
{
private:
	struct graph_vertex;

public:
	typedef boost::variant<range_tag, fullexpr_tag> node_tag_type;
	typedef std::size_t node_tag_ref;

	typedef graph_vertex * vertex_descriptor;

	enum node_type { nt_none, nt_exit, nt_value, nt_call, nt_phi };
	enum op_type { ot_none, ot_func, ot_oper, ot_const, ot_member, ot_node, ot_var, ot_varptr };

	typedef boost::variant<boost::none_t, std::string, vertex_descriptor> op_id;

	struct operand
	{
		op_type type;
		op_id id;

		operand(op_type type = ot_none, op_id id = boost::none)
			: type(type), id(id)
		{
		}

		friend bool operator==(operand const & lhs, operand const & rhs)
		{
			return lhs.type == rhs.type && lhs.id == rhs.id;
		}

		friend bool operator!=(operand const & lhs, operand const & rhs)
		{
			return !(lhs == rhs);
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

		friend bool operator==(edge const & lhs, edge const & rhs)
		{
			return lhs.id == rhs.id && lhs.cond == rhs.cond;
		}

		friend bool operator!=(edge const & lhs, edge const & rhs)
		{
			return !(lhs == rhs);
		}
	};

	struct node
	{
		node_type type;
		std::vector<operand> ops;
		std::set<node_tag_ref> tags;

		node(node_type type = nt_none)
			: type(type)
		{
		}
	};

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

public:
	struct edge_descriptor
	{
		vertex_descriptor source;
		std::list<graph_edge>::iterator parity;

		edge_descriptor(vertex_descriptor source, std::list<graph_edge>::iterator parity)
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
			return lhs.source < rhs.source || (lhs.source == rhs.source && &*lhs.parity < &*rhs.parity);
		}
	};

	cfg()
		: m_entry(0)
	{
	}

	cfg(cfg const & other)
		: m_entry(0), m_params(other.m_params), m_locals(other.m_locals), m_tags(other.m_tags)
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
		lhs.m_tags.swap(rhs.m_tags);
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
		for (std::list<graph_edge>::iterator it = v->succs.begin(); it != v->succs.end(); ++it)
			it->target->preds.erase(edge_descriptor(v, it));
		v->succs.clear();

		for (std::set<edge_descriptor>::const_iterator it = v->preds.begin(); it != v->preds.end(); ++it)
			it->source->succs.erase(it->parity);
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
		source->succs.push_back(target);

		edge_descriptor new_edge(source, boost::prior(source->succs.end()));
		target->preds.insert(new_edge);
		return std::make_pair(new_edge, true);
	}

	void redirect_vertex(cfg::vertex_descriptor source, cfg::vertex_descriptor target)
	{
		// TODO: basic exception guarantees
		for (std::set<edge_descriptor>::const_iterator it = source->preds.begin(); it != source->preds.end(); ++it)
		{
			it->parity->target = target;
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
		return e.parity->target;
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

	edge & operator[](edge_descriptor e) { return e.parity->prop; }
	edge const & operator[](edge_descriptor e) const { return e.parity->prop; }

	struct out_edge_iterator
		: boost::iterator_facade<out_edge_iterator, edge_descriptor const, boost::bidirectional_traversal_tag>
	{
		out_edge_iterator(vertex_descriptor source, std::list<graph_edge>::iterator parity)
			: m_current(source, parity)
		{
		}

		edge_descriptor const & dereference() const { return m_current; };
		void increment() { ++m_current.parity; }
		void decrement() { --m_current.parity; }
		bool equal(out_edge_iterator const & other) const { return m_current == other.m_current; }

	private:
		edge_descriptor m_current;
	};

	friend std::pair<out_edge_iterator, out_edge_iterator> out_edges(vertex_descriptor v, cfg const & g)
	{
		return std::make_pair(out_edge_iterator(v, v->succs.begin()), out_edge_iterator(v, v->succs.end()));
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

	node_tag_ref add_tag(node_tag_type const & tag)
	{
		std::vector<node_tag_type>::const_iterator it = std::find(m_tags.begin(), m_tags.end(), tag);
		if (it != m_tags.end())
			return it - m_tags.begin();
		m_tags.push_back(tag);
		return m_tags.size() - 1;
	}

	node_tag_type const & tag(node_tag_ref tagref) const
	{
		return m_tags[tagref];
	}

private:
	struct graph_vertex
	{
		std::list<graph_edge> succs;
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
	std::vector<node_tag_type> m_tags;
};

class program
{
public:
	typedef std::multimap<std::string, std::string> vfn_map_type;
	typedef std::map<std::string, std::size_t> vfn_param_counts_type;

	void add_cfg(std::string const & name, cfg const & c)
	{
		m_cfgs.insert(std::make_pair(name, c));
	}

	std::map<std::string, cfg> const & cfgs() const { return m_cfgs; }

	filename_store const & fnames() const { return m_fnames; }
	filename_store & fnames() { return m_fnames; }

	void pretty_print(std::ostream & out) const;

	void vfn_map(vfn_map_type const & v) { m_vfn_map = v; }
	vfn_map_type const & vfn_map() const { return m_vfn_map; }

	void vfn_param_counts(vfn_param_counts_type const & v) { m_vfn_param_counts = v; }
	vfn_param_counts_type const & vfn_param_counts() const { return m_vfn_param_counts; }

private:
	std::map<std::string, cfg> m_cfgs;
	filename_store m_fnames;

	vfn_map_type m_vfn_map;
	vfn_param_counts_type m_vfn_param_counts;
};

#endif
