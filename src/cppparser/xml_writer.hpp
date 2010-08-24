#ifndef XML_WRITER_HPP
#define XML_WRITER_HPP

#include "ast_dumper_detail.hpp"

#include <string>
#include <ostream>
#include <boost/lexical_cast.hpp>

class xml_node
{
public:
	xml_node(std::ostream & out, std::string const & name)
		: m_out(out), m_name(name), m_header_done(false)
	{
		m_out << "<" << m_name;
	}

	xml_node(xml_node & node, std::string const & name)
		: m_out(node.m_out), m_name(name), m_header_done(false)
	{
		node.header_done();
		m_out << "<" << m_name;
	}

	~xml_node()
	{
		if (!m_header_done)
			m_out << "/>";
		else
			m_out << "</" << m_name << ">";
	}

	void attr(std::string const & key, std::string const & value)
	{
		BOOST_ASSERT(!m_header_done);
		m_out << " " << key << "=\"" << xml_escape(value) << "\"";
	}

	template <typename T>
	void attr(std::string const & key, T const & t)
	{
		this->attr(key, boost::lexical_cast<std::string>(t));
	}

	void text(std::string const & value)
	{
		this->header_done();
		m_out << xml_escape(value);
	}

	template <typename T>
	void text(T const & t)
	{
		this->text(boost::lexical_cast<std::string>(t));
	}

	void header_done()
	{
		if (!m_header_done)
		{
			m_out << ">";
			m_header_done = true;
		}
	}

private:
	std::ostream & m_out;
	std::string m_name;
	bool m_header_done;
};

#endif
