#include "ast_dumper.hpp"
#include "rcfg_dumper.hpp"

#include "cfg.hpp"
#include "cfg_builder.hpp"
#include "cfg_json_writer.hpp"

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

#include <iostream>
#include <set>

#include <boost/utility.hpp>
#include <boost/assert.hpp>

struct config
{
	bool printAST;
	bool printRCFG;
	int printJsonCfg;
	bool printReadableAST;
	bool printUnitAST;
	bool debugCFG;

	bool showFnNames;
	std::string filter;
};

struct cfg_build_visitor
	: default_build_visitor
{
	explicit cfg_build_visitor(bool show_names, std::string const & filter)
		: m_show_names(show_names), m_filter(filter)
	{
	}

	bool function_started(std::string const & name)
	{
		if (!m_filter.empty() && name.substr(0, m_filter.size()) != m_filter)
			return false;

		if (m_show_names)
			std::cerr << name << std::endl;

		return true;
	}

	bool m_show_names;
	std::string m_filter;
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

		std::set<clang::FunctionDecl const *> functionDecls;
		get_used_function_defs(ctx, functionDecls);
		if (!m_c.filter.empty())
		{
			std::set<clang::FunctionDecl const *> filtered;
			for (std::set<clang::FunctionDecl const *>::const_iterator ci = functionDecls.begin(); ci != functionDecls.end(); ++ci)
			{
				if ((*ci)->getQualifiedNameAsString() == m_c.filter)
					filtered.insert(*ci);
			}
			functionDecls.swap(filtered);
		}

		if (m_c.printReadableAST)
			print_readable_ast(std::cout, ctx, functionDecls.begin(), functionDecls.end());

		if (m_c.printJsonCfg)
		{
			program prog = build_program(ctx.getTranslationUnitDecl(), cfg_build_visitor(m_c.showFnNames, m_c.filter));
			cfg_json_write(std::cout, prog, m_c.printJsonCfg == 1);
		}

		if (m_c.printAST)
			print_ast(std::cout, ctx, functionDecls.begin(), functionDecls.end());

		if (m_c.printRCFG)
			print_rcfg(ctx, std::cout, &ctx.getSourceManager(), functionDecls.begin(), functionDecls.end());

		if (m_c.printUnitAST)
			print_decl(ctx.getTranslationUnitDecl(), std::cout, 0);

		if (m_c.debugCFG)
		{
			program prog = build_program(ctx.getTranslationUnitDecl(), cfg_build_visitor(m_c.showFnNames, m_c.filter));
			prog.pretty_print(std::cerr);
			//print_debug_rcfg(ctx, std::cerr, &ctx.getSourceManager(), functionDecls.begin(), functionDecls.end());
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

		// Parse the arguments
		for (int i = 1; i < argc; ++i)
		{
			std::string arg = argv[i];
			if (arg == "-a")
				c.printReadableAST = true;
			else if (arg == "-A")
				c.printAST = true;
			else if (arg == "-j")
				c.printJsonCfg = 1;
			else if (arg == "-J")
				c.printJsonCfg = 2;
			else if (arg == "-r")
				c.printRCFG = true;
			else if (arg == "-u")
				c.printUnitAST = true;
			else if (arg == "--debugcfg")
				c.debugCFG = true;
			else if (arg == "--showfnnames")
				c.showFnNames = true;
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

		if (!c.printJsonCfg && !c.printReadableAST && !c.printAST && !c.debugCFG && !c.printUnitAST && !c.printRCFG)
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
