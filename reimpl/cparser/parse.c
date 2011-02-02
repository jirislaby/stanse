#include <stdlib.h>

#include <antlr3defs.h>

#include "GNUCaLexer.h"
#include "GNUCaParser.h"
#include "ASTEmitter.h"
#include "common.h"

int parse(const char *file)
{
	pANTLR3_INPUT_STREAM input;
	pANTLR3_COMMON_TOKEN_STREAM tstream;
	pANTLR3_COMMON_TREE_NODE_STREAM tnstream;
	GNUCaParser_translationUnit_return parseTree;
	pGNUCaLexer lxr;
	pGNUCaParser psr;
	pASTEmitter ASTe;
	pANTLR3_LIST AST_list;
	int ret = 1;

	input = antlr3AsciiFileStreamNew((unsigned char *)file);
	if (!input)
		goto err;
	ret++;
	lxr = GNUCaLexerNew(input);
	if (!lxr)
		goto err_input;
	ret++;
	tstream = antlr3CommonTokenStreamSourceNew(ANTLR3_SIZE_HINT, TOKENSOURCE(lxr));
	if (!tstream)
		goto err_lxr;
	ret++;
	psr = GNUCaParserNew(tstream);
	if (!psr)
		goto err_tstream;

	parseTree = psr->translationUnit(psr);

//	puts((char *)parseTree.tree->toStringTree(parseTree.tree)->chars);

	ret++;
	tnstream = antlr3CommonTreeNodeStreamNewTree(parseTree.tree, ANTLR3_SIZE_HINT);
	if (!tnstream)
		goto err_psr;

	ret++;
	ASTe = ASTEmitterNew(tnstream);
	if (!ASTe)
		goto err_tnstream;

	ASTe->translationUnit(ASTe);

	ret = 0;

	ASTe->free(ASTe);
err_tnstream:
	tnstream->free(tnstream);
err_psr:
	psr->free(psr);
err_tstream:
	tstream->free(tstream);
err_lxr:
	lxr->free(lxr);
err_input:
	input->close(input);
err:
	return ret;
}
