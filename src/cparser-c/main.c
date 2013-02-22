#include <stdlib.h>

#include <antlr3defs.h>

#include "GNUCaLexer.h"
#include "GNUCaParser.h"

int main(int argc, char *argv[])
{
	pANTLR3_INPUT_STREAM input;
	pANTLR3_COMMON_TOKEN_STREAM tstream;
	pGNUCaLexer lxr;
	pGNUCaParser psr;
	int ret = 1;

	if (argc != 2)
		goto err;

	ret++;
	input = antlr3FileStreamNew((unsigned char *)argv[1], ANTLR3_ENC_8BIT);
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
