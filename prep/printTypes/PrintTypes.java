import java.io.*;
import org.antlr.runtime.*;

public class PrintTypes {

    public static void main(String args[]) throws Exception {
        GNUCtLexer lex = new GNUCtLexer(new ANTLRFileStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        GNUCtParser g = new GNUCtParser(tokens);

        try {
            g.translationUnit();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }
}
