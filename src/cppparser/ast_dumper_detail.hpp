#ifndef AST_DUMPER_DETAIL_HPP
#define AST_DUMPER_DETAIL_HPP

#include <clang/AST/ASTContext.h>
#include <clang/Basic/SourceManager.h>

class xml_printer
{
public:
	explicit xml_printer(std::ostream & fout, clang::SourceManager const * sm = 0);

	void xml_print_tag(std::string const & tag_name, clang::SourceLocation sl, std::string const & extra = "");
	void xml_print_type(clang::QualType type);
	void xml_print_decl_name(clang::NamedDecl const * decl);
	void xml_print_expr(clang::Expr const * expr);
	void xml_print_statement(clang::Stmt const * stmt);

private:
	std::ostream & fout;
	clang::SourceManager const * m_sm;
};

std::string make_decl_name(clang::NamedDecl const * decl);

void print_decl(clang::Decl const * decl, std::ostream & out, int level);
void print_stmt(clang::Stmt const * stmt, std::ostream & out, int level);

#endif
