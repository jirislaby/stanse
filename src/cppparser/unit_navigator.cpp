#include "unit_navigator.hpp"
#include "util.hpp"

#include <clang/AST/DeclCXX.h>
#include <clang/AST/DeclTemplate.h>

void unit_navigator::filter(std::string const & prefix)
{
	std::set<clang::FunctionDecl const *> fns;
	for (std::set<clang::FunctionDecl const *>::const_iterator it = m_fns.begin(); it != m_fns.end(); ++it)
	{
		clang::FunctionDecl const * fn = *it;
		if (make_decl_name(fn).substr(0, prefix.size()) != prefix)
			fns.insert(fn);
	}

	fns.swap(m_fns);
}

void unit_navigator::build(clang::DeclContext const * declctx)
{
	for (clang::DeclContext::decl_iterator it = declctx->decls_begin(); it != declctx->decls_end(); ++it)
	{
		clang::Decl * decl = *it;

#ifndef NDEBUG
		std::string name;
		if (clang::NamedDecl const * nd = llvm::dyn_cast<clang::NamedDecl>(decl))
			name = nd->getQualifiedNameAsString();
#endif

		if (clang::NamespaceDecl * nsdecl = dyn_cast<clang::NamespaceDecl>(decl))
		{
			this->build(nsdecl);
		}
		else if (clang::FunctionDecl * fnDecl = dyn_cast<clang::FunctionDecl>(decl))
		{
			if (fnDecl->isDependentContext())
				continue;
			m_fns.insert(fnDecl);
		}
		else if (clang::RecordDecl const * classDecl = dyn_cast<clang::RecordDecl>(decl))
		{
			this->build(classDecl);
		}
		else if (clang::FunctionTemplateDecl const * d = llvm::dyn_cast<clang::FunctionTemplateDecl>(decl))
		{
			for (clang::FunctionTemplateDecl::spec_iterator it = ((clang::FunctionTemplateDecl *)d)->spec_begin(); it != ((clang::FunctionTemplateDecl *)d)->spec_end(); ++it)
			{
				if ((*it)->isDependentContext())
					continue;
				m_fns.insert(*it);
			}
		}
		else if (clang::ClassTemplateDecl const * d = llvm::dyn_cast<clang::ClassTemplateDecl>(decl))
		{
			for (clang::ClassTemplateDecl::spec_iterator it = ((clang::ClassTemplateDecl *)d)->spec_begin(); it != ((clang::ClassTemplateDecl *)d)->spec_end(); ++it)
				this->build(*it);
		}
		else if (clang::LinkageSpecDecl const * lsdecl = llvm::dyn_cast<clang::LinkageSpecDecl>(decl))
		{
			this->build(lsdecl);
		}
	}
}
