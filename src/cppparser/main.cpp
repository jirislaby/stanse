#include "cfg.hpp"
#include "cfg_builder.hpp"
#include "cfg_json_writer.hpp"

#include "ast_pretty_printer.hpp"
#include "unit_navigator.hpp"

#include <clang/Lex/Preprocessor.h>
#include <clang/Basic/SourceManager.h>
#include <clang/Basic/FileManager.h>
#include <clang/Basic/TargetInfo.h>
#include <clang/Lex/HeaderSearch.h>
#include <clang/AST/ASTContext.h>
#include <clang/Frontend/CompilerInvocation.h>
#include <clang/AST/ASTConsumer.h>
#include <clang/AST/Decl.h>
#include <clang/AST/Stmt.h>
#include <clang/AST/DeclTemplate.h>
#include <clang/AST/DeclCXX.h>
#include <clang/Analysis/CFG.h>
#include <clang/Frontend/Utils.h>
#include <clang/Frontend/CompilerInstance.h>
#include <clang/Frontend/FrontendActions.h>
#include <clang/Frontend/TextDiagnosticBuffer.h>
#include <clang/Sema/Sema.h>

#include <iostream>
#include <set>

#include <boost/utility.hpp>
#include <boost/assert.hpp>

struct config
{
	int printJsonCfg;
	bool printReadableAST;
	bool printUnitAST;
	bool buildCfg;
	bool debugCFG;

	bool showFnNames;
	bool dump_progress;
	std::string filter;

	std::string static_prefix;
};

struct cfg_build_visitor
	: default_build_visitor
{
	cfg_build_visitor(clang::CompilerInstance & ci, config const & c)
		: m_ci(ci), m_c(c)
	{
	}

	bool function_started(std::string const & name)
	{
		if (!m_c.filter.empty() && name.substr(0, m_c.filter.size()) != m_c.filter)
			return false;

		if (m_c.showFnNames)
			std::cerr << name << std::endl;

		return true;
	}

	void statement_visited(clang::Stmt const * stmt)
	{
		if (m_c.dump_progress)
		{
			stmt->getLocStart().dump(m_ci.getSourceManager());
			std::cerr << '\n';
		}
	}

	clang::CompilerInstance & m_ci;
	config const & m_c;
};

class MyConsumer
	: public clang::ASTConsumer
{
public:
	MyConsumer(clang::CompilerInstance & ci, config const & c, std::string const & filename)
		: m_ci(ci), m_c(c), m_filename(filename)
	{
	}

	void HandleTranslationUnit(clang::ASTContext &ctx)
	{
		if (m_ci.getDiagnostics().getNumErrors() > 0)
		{
			std::cerr << "Errors were found, serialization of AST and CFGs is disabled." << std::endl;
			return;
		}

#if 0
		std::vector<clang::CXXRecordDecl *> structure_decls;
		for (clang::ASTContext::type_iterator it = ctx.types_begin(); it != ctx.types_end(); ++it)
		{
			clang::Type * type = *it;
			if (type->isDependentType() || !type->isStructureOrClassType())
				continue;

			structure_decls.push_back(type->getAsCXXRecordDecl());
		}

		// Instantiate all special member functions
		for (std::size_t i = 0; i != structure_decls.size(); ++i)
		{
			clang::CXXRecordDecl * recdecl = structure_decls[i];
			for (clang::CXXRecordDecl::method_iterator mit = recdecl->method_begin(); mit != recdecl->method_end(); ++mit)
			{
				clang::CXXMethodDecl * method = *mit;
				m_ci.getSema().MarkDeclarationReferenced(method->getLocation(), method);
			}
		}
#endif

		unit_navigator un(ctx.getTranslationUnitDecl());
		if (!m_c.filter.empty())
			un.filter(m_c.filter);

		if (m_c.printReadableAST)
		{
			for (unit_navigator::fns_const_iterator ci = un.fns_begin(); ci != un.fns_end(); ++ci)
				pretty_print_decl(*ci, std::cout);
		}

		if (m_c.buildCfg)
			build_program(un, m_ci.getSourceManager(), cfg_build_visitor(m_ci, m_c), m_c.static_prefix);

		if (m_c.printJsonCfg)
		{
			program prog = build_program(un, m_ci.getSourceManager(), cfg_build_visitor(m_ci, m_c), m_c.static_prefix);
			cfg_json_write(std::cout, prog, m_c.printJsonCfg == 1);
		}

		if (m_c.printUnitAST)
			pretty_print_decl(ctx.getTranslationUnitDecl(), std::cout, 0);

		if (m_c.debugCFG)
		{
			program prog = build_program(un, m_ci.getSourceManager(), cfg_build_visitor(m_ci, m_c), m_c.static_prefix);
			prog.pretty_print(std::cerr);
		}
	}

private:
	clang::CompilerInstance & m_ci;
	config m_c;
	std::string m_filename;
};

class MyASTDumpAction : public clang::ASTFrontendAction
{
public:
	MyASTDumpAction(config const & c)
		: m_c(c)
	{
	}

	clang::ASTContext * ctx;

protected:
	virtual clang::ASTConsumer *CreateASTConsumer(clang::CompilerInstance &CI,
		llvm::StringRef InFile)
	{
		return new MyConsumer(CI, m_c, InFile.str());
	}

private:
	config m_c;
};

class MyDiagClient : public clang::DiagnosticClient
{
public:
	virtual void HandleDiagnostic(clang::Diagnostic::Level DiagLevel, const clang::DiagnosticInfo &Info)
	{
	}
};

int main(int argc, char * argv[])
{
	try
	{
		static char const * additional_args[] = {
			"-triple", "i686-pc-win32",
			"-ferror-limit", "19",
			"-fmessage-length", "300",
			"-fexceptions",
			"-fms-extensions",
			"-fgnu-runtime",
			"-fdiagnostics-show-option",
			"-fcolor-diagnostics",
			"-x", "c++",
			"-nobuiltininc",
		};

		std::vector<char const *> args(additional_args, additional_args + sizeof additional_args / sizeof additional_args[0]);

		config c = {};
		c.static_prefix = "__unique";

		// Parse the arguments
		for (int i = 1; i < argc; ++i)
		{
			std::string arg = argv[i];
			if (arg == "-a")
				c.printReadableAST = true;
			else if (arg == "-j")
				c.printJsonCfg = 1;
			else if (arg == "-J")
				c.printJsonCfg = 2;
			else if (arg == "-u")
				c.printUnitAST = true;
			else if (arg == "-c")
				c.buildCfg = true;
			else if (arg == "--debugcfg")
				c.debugCFG = true;
			else if (arg == "--showfnnames")
				c.showFnNames = true;
			else if (arg == "--dumpprogress")
				c.dump_progress = true;
			else if (arg == "--staticprefix" && i + 1 < argc)
				c.static_prefix = argv[++i];
			else if (arg == "--filter" && i + 1 < argc)
				c.filter = argv[++i];
			else
				args.push_back(argv[i]);
		}

		clang::CompilerInstance comp_inst;
		clang::TextDiagnosticBuffer * argDiagBuffer = new clang::TextDiagnosticBuffer();
		clang::Diagnostic * argDiag = new clang::Diagnostic(argDiagBuffer);

		clang::CompilerInvocation & ci = comp_inst.getInvocation();
		clang::CompilerInvocation::CreateFromArgs(ci, &args.front(), &args.back() + 1, *argDiag);

		comp_inst.createDiagnostics(args.size(), (char **)&args[0]);
		argDiagBuffer->FlushDiagnostics(comp_inst.getDiagnostics());

		if (!c.printJsonCfg && !c.printReadableAST && !c.debugCFG && !c.printUnitAST && !c.buildCfg)
			c.printReadableAST = true;

		MyASTDumpAction act(c);
		comp_inst.ExecuteAction(act);

		return comp_inst.getDiagnostics().getNumErrors();
	}
	catch (std::exception const & e)
	{
		std::cerr << "error: " << e.what() << std::endl;
		return 1;
	}
	catch (...)
	{
		std::cerr << "error: unexpected error occured" << std::endl;
		return 1;
	}
}
