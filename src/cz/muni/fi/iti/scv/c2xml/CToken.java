package cz.muni.fi.iti.scv.c2xml;
import antlr.CommonToken;

public class CToken extends ExtentToken {
  String source = "";
  int tokenNumber;

  public String getSource() 
  { 
    return source;
  }

  public void setSource(String src) 
  {
    source = src;
  }

  public int getTokenNumber() 
  { 
    return tokenNumber;
  }

  public void setTokenNumber(int i) 
  {
    tokenNumber = i;
  }

    public String toString() {
        return "CToken:" +"(" + hashCode() + ")" + "[" + getType() + "] "+ getText() + " line:" + getLine() + " source:" + source ;
    }
}
