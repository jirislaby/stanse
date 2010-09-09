#include "cfg.hpp"

void program::pretty_print(std::ostream & out) const
{
	for (std::map<std::string, cfg>::const_iterator it = m_cfgs.begin(); it != m_cfgs.end(); ++it)
	{
		out << "def " << it->first << ":\n";

		cfg const & c = it->second;

		std::map<cfg::vertex_descriptor, std::size_t> index_map;
		std::pair<cfg::vertex_iterator, cfg::vertex_iterator> vertices_range = vertices(c);
		for (cfg::vertex_iterator it = vertices_range.first; it != vertices_range.second; ++it)
		{
			std::size_t vidx = index_map.size();
			index_map[*it] = vidx;
		}

		out << "    entry " << index_map[c.entry()] << "\n";

		for (std::size_t i = 0; i < c.params().size(); ++i)
			out << "    param: " << c.params()[i] << "\n";

		for (std::size_t i = 0; i < c.locals().size(); ++i)
			out << "    var: " << c.locals()[i] << "\n";

		vertices_range = vertices(c);
		for (cfg::vertex_iterator it = vertices_range.first; it != vertices_range.second; ++it)
		{
			cfg::node const & node = c[*it];

			out << "    node " << index_map[*it] << " ";

			switch (node.type)
			{
			case cfg::nt_none:
				out << "none";
				break;
			case cfg::nt_exit:
				out << "exit";
				break;
			case cfg::nt_value:
				out << "value";
				break;
			case cfg::nt_call:
				out << "call";
				break;
			case cfg::nt_phi:
				out << "phi";
				break;
			default:
				BOOST_ASSERT(0 && "Unknown node type");
			}

			out << '\n';

			for (std::size_t j = 0; j < node.ops.size(); ++j)
			{
				cfg::operand const & op = node.ops[j];

				static char const * op_names[] = { "none", "func", "oper", "const", "member", "node", "var", "varptr" };

				BOOST_ASSERT(op.type <= cfg::ot_varptr);
				out << "        " << op_names[op.type] << " ";

				if (op.id.which() == 2)
				{
					cfg::vertex_descriptor op_node = boost::get<cfg::vertex_descriptor>(op.id);
					out << index_map[op_node];
				}
				else
				{
					out << op.id;
				}

				out << "\n";
			}

			std::pair<cfg::out_edge_iterator, cfg::out_edge_iterator> out_edges_range = out_edges(*it, c);
			for (cfg::out_edge_iterator edge_it = out_edges_range.first; edge_it != out_edges_range.second; ++edge_it)
			{
				out << "        succ " << index_map[target(*edge_it, c)] << " " << c[*edge_it].id << " " << c[*edge_it].cond << '\n';
			}
		}

		out << std::endl;
	}
}
