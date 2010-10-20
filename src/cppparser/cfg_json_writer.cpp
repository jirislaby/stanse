#include "cfg_json_writer.hpp"
#include "json/json.h"
#include <boost/assert.hpp>
#include <boost/range.hpp>

namespace {

class context
{
public:
	static Json::Value serialize(program const & prog)
	{
		return context().serialize_impl(prog);
	}

private:
	Json::Value serialize_impl(program const & prog)
	{
		Json::Value json_cfgs(Json::objectValue);

		for (std::map<std::string, cfg>::const_iterator it = prog.cfgs().begin(); it != prog.cfgs().end(); ++it)
		{
			cfg const & c = it->second;

			std::map<cfg::vertex_descriptor, std::size_t> index_map;
			std::pair<cfg::vertex_iterator, cfg::vertex_iterator> vertices_range = vertices(c);
			for (cfg::vertex_iterator it = vertices_range.first; it != vertices_range.second; ++it)
			{
				std::size_t vidx = index_map.size();
				index_map[*it] = vidx;
			}

			std::map<cfg::node_tag_ref, std::size_t> tag_map;

			Json::Value json_nodes(Json::arrayValue);

			vertices_range = vertices(c);
			for (cfg::vertex_iterator it = vertices_range.first; it != vertices_range.second; ++it)
			{
				cfg::node const & node = c[*it];

				Json::Value json_node(Json::arrayValue);
				json_node.resize(3);
				switch (node.type)
				{
				case cfg::nt_none:
					json_node[0u] = "none";
					break;
				case cfg::nt_exit:
					json_node[0u] = "exit";
					break;
				case cfg::nt_value:
					json_node[0u] = "value";
					break;
				case cfg::nt_call:
					json_node[0u] = "call";
					break;
				case cfg::nt_phi:
					json_node[0u] = "phi";
					break;
				default:
					BOOST_ASSERT(0 && "Unknown node type");
				}

				json_node[1] = Json::Value(Json::arrayValue);

				std::pair<cfg::out_edge_iterator, cfg::out_edge_iterator> out_edges_range = out_edges(*it, c);
				for (cfg::out_edge_iterator edge_it = out_edges_range.first; edge_it != out_edges_range.second; ++edge_it)
				{
					Json::Value json_succ(Json::arrayValue);
					json_succ.append((Json::UInt)index_map[target(*edge_it, c)]);
					json_succ.append((Json::UInt)c[*edge_it].id);
					json_succ.append(c[*edge_it].cond);
					json_node[1].append(json_succ);
				}

				json_node[2] = Json::Value(Json::arrayValue);
				for (std::size_t i = 0; i < node.ops.size(); ++i)
				{
					Json::Value json_op(Json::arrayValue);
					switch (node.ops[i].type)
					{
					case cfg::ot_none:
						json_op.append("none");
						break;
					case cfg::ot_func:
						json_op.append("func");
						break;
					case cfg::ot_oper:
						json_op.append("oper");
						break;
					case cfg::ot_const:
						json_op.append("const");
						break;
					case cfg::ot_member:
						json_op.append("member");
						break;
					case cfg::ot_node:
						json_op.append("node");
						break;
					case cfg::ot_var:
						json_op.append("var");
						break;
					case cfg::ot_varptr:
						json_op.append("varptr");
						break;
					default:
						BOOST_ASSERT(0 && "Unknown operand type");
					}

					switch (node.ops[i].id.which())
					{
					case 0:
						json_op.append(Json::nullValue);
						break;
					case 1:
						json_op.append(boost::get<std::string>(node.ops[i].id));
						break;
					case 2:
						json_op.append((Json::UInt)index_map[boost::get<cfg::vertex_descriptor>(node.ops[i].id)]);
						break;
					}

					json_node[2].append(json_op);
				}

				if (!node.tags.empty())
				{
					json_node.append(Json::Value(Json::arrayValue));
					for (std::set<cfg::node_tag_ref>::const_iterator ci = node.tags.begin(); ci != node.tags.end(); ++ci)
					{
						std::size_t tagid = tag_map.insert(std::make_pair(*ci, tag_map.size())).first->second;
						json_node[3].append(Json::Int(tagid));
					}
				}

				json_nodes.append(json_node);
			}

			Json::Value json_cfg(Json::objectValue);

			Json::Value json_tags(Json::arrayValue);
			json_tags.resize(tag_map.size());
			for (std::map<cfg::node_tag_ref, std::size_t>::const_iterator ci = tag_map.begin(); ci != tag_map.end(); ++ci)
			{
				tag_visitor v(*this);
				json_tags[ci->second] = c.tag(ci->first).apply_visitor(v);
			}
			json_cfg["tags"] = json_tags;

			json_cfg["params"] = Json::Value(Json::arrayValue);
			for (std::size_t i = 0; i < c.params().size(); ++i)
				json_cfg["params"].append(c.params()[i]);

			json_cfg["locals"] = Json::Value(Json::arrayValue);
			for (std::size_t i = 0; i < c.locals().size(); ++i)
				json_cfg["locals"].append(c.locals()[i]);

			json_cfg["nodes"] = json_nodes;
			json_cfg["entry"] = (Json::UInt)index_map[c.entry()];

			json_cfgs[it->first] = json_cfg;
		}

		Json::Value json_prog(Json::objectValue);
		json_prog["cfgs"] = json_cfgs;
		
		Json::Value json_fnames(Json::arrayValue);
		json_fnames.resize(prog.fnames().size());
		for (std::size_t i = 0; i != prog.fnames().size(); ++i)
			json_fnames[i] = Json::Value(prog.fnames()[i]);
		json_prog["filenames"] = json_fnames;

		Json::Value json_vfn_counts(Json::objectValue);
		program::vfn_param_counts_type const & param_counts = prog.vfn_param_counts();
		for (program::vfn_param_counts_type::const_iterator it = param_counts.begin();
			it != param_counts.end(); ++it)
		{
			json_vfn_counts[it->first] = Json::Int(it->second);
		}
		json_prog["_vfn_param_counts"] = json_vfn_counts;

		Json::Value json_vfn_map(Json::objectValue);
		program::vfn_map_type const & vfn_map = prog.vfn_map();
		for (program::vfn_map_type::const_iterator it = vfn_map.begin(); it != vfn_map.end(); ++it)
		{
			Json::Value & entry = json_vfn_map[it->first];
			if (entry.type() != Json::arrayValue)
				entry = Json::Value(Json::arrayValue);
			entry.append(it->second);
		}
		json_prog["_vfn_map"] = json_vfn_map;

		return json_prog;
	}

private:
	std::set<std::size_t> m_referenced_fnames;

	struct tag_visitor
	{
		typedef Json::Value result_type;

		explicit tag_visitor(context & parent) : m_parent(parent) {}
		context & m_parent;

		Json::Value operator()(range_tag const & t)
		{
			m_parent.m_referenced_fnames.insert(t.fname);

			Json::Value res(Json::arrayValue);
			res.append(Json::Value("source_range"));
			res.append(Json::Int(t.fname));
			res.append(t.start_line);
			res.append(t.start_col);
			res.append(t.end_line);
			res.append(t.end_col);
			return res;
		}

		Json::Value operator()(fullexpr_tag const &)
		{
			// TODO
			return Json::Value(Json::arrayValue).append(Json::Value("fullexpr"));
		}
	};
};

}

void cfg_json_write(std::ostream & out, program const & prog, bool readable)
{
	Json::Value json_prog = context::serialize(prog);
	if (readable)
	{
		Json::StyledWriter writer;
		out << writer.write(json_prog);
	}
	else
	{
		Json::FastWriter writer;
		out << writer.write(json_prog);
	}
}
