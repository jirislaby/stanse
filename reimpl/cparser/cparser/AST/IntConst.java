/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.AST.ComplexType.COMPLEX_TYPE;
import java.math.BigInteger;

/**
 * @author Jiri Slaby
 */
public class IntConst extends Constant {
	private boolean hex = false;
	private boolean octal = false;
	private boolean unsigned = false;
	private int ls = 0;

	public IntConst(final String val) {
		super(val);
		assert(val.length() > 0);
	}

	public BigInteger getValue() {
		return (BigInteger)eval;
	}

	@Override
	public void setAttrII(final int key, final int val) {
		switch (key) {
		case 0:
			if (val == 1)
				hex = true;
			else if (val == 2)
				octal = true;
			break;
		case 1:
			ls = val;
			break;
		case 2:
			if (val > 0)
				unsigned = true;
			break;
		}
	}

	private void eval() {
		int radix = 10;
		if (octal)
			radix = 8;
		else if (hex)
			radix = 16;
		eval = new BigInteger(val, radix);
	}

	private static final BigInteger maxInt = new BigInteger("7fffffff", 16);
	private static final BigInteger minInt = new BigInteger("-7fffffff", 16);
	private static final BigInteger maxUInt = new BigInteger("ffffffff", 16);
	private static final BigInteger minUInt = BigInteger.ZERO;
	private static final BigInteger maxLL = new BigInteger("7fffffffffffffff", 16);
	private static final BigInteger minLL = new BigInteger("-7fffffffffffffff", 16);
	private static final BigInteger maxULL = new BigInteger("ffffffffffffffff", 16);
	private static final BigInteger minULL = BigInteger.ZERO;

	/* differs for 32/64-bit */
	private static BigInteger minL = minLL;
	private static BigInteger maxL = maxLL;
	private static BigInteger minUL = minULL;
	private static BigInteger maxUL = maxULL;

	private static boolean between(final BigInteger val,
			final BigInteger low, final BigInteger high) {
		return val.compareTo(low) >= 0 && val.compareTo(high) <= 0;
	}

	private boolean valFits(final COMPLEX_TYPE type) {
		final BigInteger myVal = getValue();
		switch (type) {
		case INT:
			return between(myVal, minInt, maxInt);
		case UINT:
			return between(myVal, minUInt, maxUInt);
		case LONG:
			return between(myVal, minL, maxL);
		case ULONG:
			return between(myVal, minUL, maxUL);
		case LONGLONG:
			return between(myVal, minLL, maxLL);
		case ULONGLONG:
			return between(myVal, minULL, maxULL);
		default:
			throw new RuntimeException("invalid type: " + type);
		}
	}

	@Override
	public void compute() {
		super.compute();
		eval();
		boolean octOrHex = octal || hex;
		COMPLEX_TYPE cType;
		if (unsigned && ls == 2)
			cType = COMPLEX_TYPE.ULONGLONG;
		else if (ls == 2) {
			if (octOrHex)
				cType = valFits(COMPLEX_TYPE.LONGLONG) ?
					COMPLEX_TYPE.LONGLONG :
					COMPLEX_TYPE.ULONGLONG;
			else
				cType = COMPLEX_TYPE.LONGLONG;
		} else if (unsigned && ls == 1) {
			cType = valFits(COMPLEX_TYPE.ULONG) ?
				COMPLEX_TYPE.ULONG :
				COMPLEX_TYPE.ULONGLONG;
		} else if (ls == 1) {
			if (octOrHex) {
				if (valFits(COMPLEX_TYPE.LONG))
					cType = COMPLEX_TYPE.LONG;
				else if (valFits(COMPLEX_TYPE.ULONG))
					cType = COMPLEX_TYPE.ULONG;
				else if (valFits(COMPLEX_TYPE.LONGLONG))
					cType = COMPLEX_TYPE.LONGLONG;
				else /*if (valFits(COMPLEX_TYPE.ULONGLONG))*/
					cType = COMPLEX_TYPE.ULONGLONG;
			} else
				cType = valFits(COMPLEX_TYPE.LONG) ?
					COMPLEX_TYPE.LONG :
					COMPLEX_TYPE.LONGLONG;
		} else if (unsigned) {
			if (valFits(COMPLEX_TYPE.UINT))
				cType = COMPLEX_TYPE.UINT;
			else if (valFits(COMPLEX_TYPE.ULONG))
				cType = COMPLEX_TYPE.ULONG;
			else /*if (valFits(COMPLEX_TYPE.ULONGLONG))*/
				cType = COMPLEX_TYPE.ULONGLONG;
		} else {
			if (octOrHex) {
				if (valFits(COMPLEX_TYPE.INT))
					cType = COMPLEX_TYPE.INT;
				else if (valFits(COMPLEX_TYPE.UINT))
					cType = COMPLEX_TYPE.UINT;
				else if (valFits(COMPLEX_TYPE.LONG))
					cType = COMPLEX_TYPE.LONG;
				else if (valFits(COMPLEX_TYPE.ULONG))
					cType = COMPLEX_TYPE.ULONG;
				else if (valFits(COMPLEX_TYPE.LONGLONG))
					cType = COMPLEX_TYPE.LONGLONG;
				else /*if (valFits(COMPLEX_TYPE.ULONGLONG))*/
					cType = COMPLEX_TYPE.ULONGLONG;
			} else {
				if (valFits(COMPLEX_TYPE.INT))
					cType = COMPLEX_TYPE.INT;
				else if (valFits(COMPLEX_TYPE.LONG))
					cType = COMPLEX_TYPE.LONG;
				else /*if (valFits(COMPLEX_TYPE.LONGLONG))*/
					cType = COMPLEX_TYPE.LONGLONG;
			}
		}
		type = new ComplexType(cType);
	}

	@Override
	void XMLChildren(final StringBuilder sb) {
		if (hex)
			sb.append("0x");
		else if (octal && !val.equals("0"))
			sb.append('0');
		sb.append(val);
		if (unsigned)
			sb.append('U');
		for (int a = 0; a < ls; a++)
			sb.append('L');
	}
}
