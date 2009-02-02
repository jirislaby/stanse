#include <stdlib.h>

#include "GNUCaLexer.h"
#include "GNUCaParser.h"

int main(int argc, char *argv[])
{
	pANTLR3_UINT8 fName;
	pANTLR3_INPUT_STREAM input;
	pANTLR3_COMMON_TOKEN_STREAM tstream;
	pGNUCaLexer lxr;
	pGNUCaParser psr;
	pANTLR3_COMMON_TREE_NODE_STREAM nodes;
	pANTLR3_STRING tree_str;
	pANTLR3_BASE_TREE base;
	int ret = 1;

	if (argc != 2)
		goto err;

	ret++;
	input = antlr3AsciiFileStreamNew(argv[1]);
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

	psr->translationUnit(psr);

	ret = 0;

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
