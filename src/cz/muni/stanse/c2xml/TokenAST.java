package cz.muni.stanse.c2xml;
import antlr.BaseAST;
import antlr.CommonToken;
import antlr.Token;
import antlr.collections.AST;

/**
 * AST node implementation which stores tokens explicitly. This
 * is handy if you'd rather derive information from tokens on an
 * as-needed basis instead of snarfing data from a token as an AST
 * is being built.
 *
 * <p>This file is in the public domain.</p>
 *
 * @author Dan Bornstein, danfuzz@milk.com
 */
public class TokenAST extends BaseAST 
{
    /** the token associated with this instance */
    private Token token;

    // ------------------------------------------------------------------------
    // constructors

    /**
     * Construct an instance which (at least initially) is not associated
     * with a token.
     */
    public TokenAST () 
    {
        token = null;
    }

    /**
     * Construct an instance which is associated with the given token.
     *
     * @param tok null-ok; the token to associate this instance with
     */
    public TokenAST (Token tok) 
    {
        initialize (tok);
    }

    // ------------------------------------------------------------------------
    // public instance methods

    /** 
     * Get the token text for this instance. If there is no token associated
     * with this instance, then this returns the empty string 
     * (<code>""</code>), not <code>null</code>.
     *
     * @return non-null; the token text
     */
    public String getText () 
    { 
        if (token == null)
        {
            return "";
        }
        else
        {
            return token.getText (); 
        }
    }

    /** 
     * Get the token type for this instance. If there is no token associated
     * with this instance, then this returns {@link Token#INVALID_TYPE}.
     *
     * @return the token type
     */
    public int getType () 
    { 
        if (token == null)
        {
            return Token.INVALID_TYPE;
        }
        else
        {
            return token.getType (); 
        }
    }

    /** 
     * Get the token associated with this instance. If there is no token
     * associated with this instance, then this returns <code>null</code>.
     *
     * @return null-ok; the token associated with this instance or
     * <code>mull</code> if there is no associated token
     */
    public Token getToken () 
    { 
        return token; 
    }

    /** 
     * Set the token associated with this instance.
     *
     * @param tok null-ok; the new token to associate with this instance
     */
    public void setToken (Token tok) 
    { 
        token = tok; 
    }

    /**
     * Initialize this instance with the given token.
     *
     * @param tok null-ok; the token to associate with this instance
     */
    public void initialize (Token tok) 
    {
        token = tok;
    }

    /**
     * Initialize this instance with the given token type and text.
     * This will construct a new {@link CommonToken} with the given
     * parameters and associate this instance with it.
     *
     * @param type the token type
     * @param text null-ok; the token text
     */
    public void initialize (int type, String text) 
    {
        initialize (new CommonToken (type, text));
    }

    /**
     * Initialize this instance based on the given {@link AST}.
     * If the given <code>AST</code> is in fact an instance of 
     * <code>TokenAST</code>, then this instance will be initialized
     * to point at the same token as the given one. If not, then this
     * instance will be initialized with the same token type and text
     * as the given one.
     *
     * @param ast non-null; the <code>AST</code> to base this instance on
     */
    public void initialize (AST ast) 
    {
        if (ast instanceof TokenAST)
        {
            initialize (((TokenAST) ast).getToken ());
        }
        else
        {
            initialize (ast.getType (), ast.getText ());
        }
    }

    /** 
     * Set the token text for this node. If this instance is already
     * associated with a token, then that token is destructively modified
     * by this operation. If not, then a new token is constructed with
     * the type {@link Token#INVALID_TYPE} and the given text.
     *
     * @param text the new token text
     */
    public void setText (String text) 
    { 
        if (token == null)
        {
            initialize (Token.INVALID_TYPE, text);
        }
        else
        {
            token.setText (text);
        }
    }

    /** 
     * Set the token type for this node. If this instance is already
     * associated with a token, then that token is destructively modified
     * by this operation. If not, then a new token is constructed with
     * the given type and an empty (<code>""</code>, not <code>null</code>)
     * text string.
     *
     * @param type the new token type
     */
    public void setType (int type) 
    { 
        if (token == null)
        {
            initialize (type, "");
        }
        else
        {
            token.setType (type);
        }
    }
}
