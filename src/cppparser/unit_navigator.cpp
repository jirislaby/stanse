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

		std::string name;
		if (clang::NamedDecl const * nd = llvm::dyn_cast<clang::NamedDecl>(decl))
			name = make_decl_name(nd);

		if (clang::NamespaceDecl * nsdecl = dyn_cast<clang::NamespaceDecl>(decl))
		{
			this->build(nsdecl);
		}
		else if (clang::CXXMethodDecl * fnDecl = dyn_cast<clang::CXXMethodDecl>(decl))
		{
			if (fnDecl->isDependentContext())
				continue;
			m_fns.insert(fnDecl);

			if (fnDecl->isVirtual() && !fnDecl->isPure())
			{
				m_vfns.insert(std::make_pair("v:" + name, name));
				m_vfn_param_count.insert(
					std::make_pair("v:" + name, 1 + fnDecl->getNumParams() + fnDecl->isVariadic()));
			}

			for (clang::CXXMethodDecl::method_iterator it = fnDecl->begin_overridden_methods();
				it != fnDecl->end_overridden_methods(); ++it)
			{
				m_vfns.insert(std::make_pair("v:" + make_decl_name(*it), name));
				m_vfn_param_count.insert(
					std::make_pair("v:" + make_decl_name(*it), 1 + fnDecl->getNumParams() + fnDecl->isVariadic()));
			}
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
