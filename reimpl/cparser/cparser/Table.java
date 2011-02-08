/*
 * Licensed under GPLv2
 */

package cparser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiri Slaby
 */
public class Table<T extends Enum> {
	Map<String,T> strVal = new HashMap<String, T>();
	String[] valStr;

	public Table(int count) {
		valStr = new String[count];
	}

	public void fill(final String op, final T sc) {
		strVal.put(op, sc);
		valStr[sc.ordinal()] = op;
	}

	public String getStr(final T val) {
		return valStr[val.ordinal()];
	}

	public String getStr(final int idx) {
		return valStr[idx];
	}

	public T getVal(final String str) {
		return strVal.get(str);
	}

/*	public abstract void setValue(final T value);

	public boolean setValue(final String sc) {
		final T s = strVal.get(sc);
		if (s == null)
			return false;
		setValue(s);
		return true;
	}*/
}
