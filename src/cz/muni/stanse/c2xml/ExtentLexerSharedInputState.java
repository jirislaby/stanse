package cz.muni.stanse.c2xml;
import antlr.LexerSharedInputState;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Extension of {@link LexerSharedInputState} that is aware of
 * file names and can annotate {@link ExtentTokens} with them and
 * with end position information.
 *
 * <p>This file is in the public domain.</p>
 *
 * @author Dan Bornstein, danfuzz@milk.com
 */
public class ExtentLexerSharedInputState
extends LexerSharedInputState
{
    /** the name of the file this instance refers to */
    private String fileName;

    // ------------------------------------------------------------------------
    // constructors

    /**
     * Construct an instance.
     *
     * @param s the input stream to use
     * @param name null-ok; the file name to associate with this instance
     */
    public ExtentLexerSharedInputState (InputStream s, String name)
    {
        super (s);
        fileName = name;
    }

    /**
     * Construct an instance. The file name is set to <code>null</code>
     * initially.
     *
     * @param s the input stream to use
     */
    public ExtentLexerSharedInputState (InputStream s)
    {
        this (s, null);
    }

    /**
     * Construct an instance which opens and reads the named file.
     *
     * @param name non-null; the name of the file to use
     */
    public ExtentLexerSharedInputState (String name)
        throws IOException
    {
        this (new FileInputStream (name), name);
    }

    // ------------------------------------------------------------------------
    // public instance methods

    /**
     * Get the current line of this instance.
     *
     * @return the current line number
     */
    public int getLine ()
    {
        return line;
    }

    /**
     * Get the current column of this instance.
     *
     * @return the current column number
     */
    public int getColumn ()
    {
        return column;
    }

    /**
     * Get the file name of this instance.
     *
     * @return null-ok; the file name
     */
    public String getFileName ()
    {
        return fileName;
    }

    /**
     * Annotate an {@link ExtentToken} based on this instance. It sets
     * the end position information as well as the file name.
     *
     * @param token non-null; the token to annotate
     */
    public void annotate (ExtentToken token)
    {
        token.setEndLine (line);
        token.setEndColumn (column);
        token.setFileName (fileName);
    }
}
