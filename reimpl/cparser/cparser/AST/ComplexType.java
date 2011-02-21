/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.Table;
import java.util.List;

/**
 * @author Jiri Slaby
 */
public class ComplexType {
	public enum COMPLEX_TYPE {
		VOID,
		CHAR,
		SCHAR,
		UCHAR,
		SHORT,
		USHORT,
		INT,
		UINT,
		LONG,
		ULONG,
		LONGLONG,
		ULONGLONG,
		FLOAT,
		DOUBLE,
		LDOUBLE,
		BOOL,
		COMPLEX,
		FCOMPLEX,
		DCOMPLEX,
		LDCOMPLEX,
		IMAGINARY,
		FIMAGINARY,
		DIMAGINARY,
		LDIMAGINARY,
		CT_SIZE
	};

	private static final Table<COMPLEX_TYPE> types;
	private COMPLEX_TYPE type;

	static {
		types = new Table<COMPLEX_TYPE>(COMPLEX_TYPE.CT_SIZE.ordinal());
		types.fill("void", COMPLEX_TYPE.VOID);
		types.fill("char", COMPLEX_TYPE.CHAR);
		types.fill("signed char", COMPLEX_TYPE.SCHAR);
		types.fill("unsigned char", COMPLEX_TYPE.UCHAR);
		types.fill("short", COMPLEX_TYPE.SHORT);
		types.fill("unsigned short", COMPLEX_TYPE.USHORT);
		types.fill("int", COMPLEX_TYPE.INT);
		types.fill("unsigned int", COMPLEX_TYPE.UINT);
		types.fill("long", COMPLEX_TYPE.LONG);
		types.fill("unsigned long", COMPLEX_TYPE.ULONG);
		types.fill("long long", COMPLEX_TYPE.LONGLONG);
		types.fill("unsigned long long", COMPLEX_TYPE.ULONGLONG);
		types.fill("float", COMPLEX_TYPE.FLOAT);
		types.fill("double", COMPLEX_TYPE.DOUBLE);
		types.fill("long double", COMPLEX_TYPE.LDOUBLE);
		types.fill("_Bool", COMPLEX_TYPE.BOOL);
		types.fill("_Complex", COMPLEX_TYPE.COMPLEX);
		types.fill("float _Complex", COMPLEX_TYPE.FCOMPLEX);
		types.fill("double _Complex", COMPLEX_TYPE.DCOMPLEX);
		types.fill("long double _Complex", COMPLEX_TYPE.LDCOMPLEX);
		types.fill("_Imaginary", COMPLEX_TYPE.IMAGINARY);
		types.fill("float _Imaginary", COMPLEX_TYPE.FIMAGINARY);
		types.fill("double _Imaginary", COMPLEX_TYPE.DIMAGINARY);
		types.fill("long double _Imaginary", COMPLEX_TYPE.LDIMAGINARY);
	};

	private ComplexType() { throw new UnsupportedOperationException(); }

	private static COMPLEX_TYPE computeType(
			final List<BaseType.BASE_TYPE> type) {
		int chars = 0, shorts = 0, ints = 0, longs = 0;
		int floats = 0, doubles = 0;
		int complexes = 0, imags = 0;
		int signeds = 0, unsigneds = 0;

		for (final BaseType.BASE_TYPE bt : type) {
			switch (bt) {
			case VOID:
				return COMPLEX_TYPE.VOID;
			case CHAR:	chars++;	break;
			case SHORT:	shorts++;	break;
			case INT:	ints++;		break;
			case LONG:	longs++;	break;
			case FLOAT:	floats++;	break;
			case DOUBLE:	doubles++;	break;
			case SIGNED:	signeds++;	break;
			case UNSIGNED:	unsigneds++;	break;
			case BOOL:
				return COMPLEX_TYPE.BOOL;
			case COMPLEX:	complexes++;	break;
			case IMAGINARY:	imags++;	break;
			}
		}
		if (complexes > 0) {
			if (floats > 0)
				return COMPLEX_TYPE.FCOMPLEX;
			if (doubles > 0 && longs > 0)
				return COMPLEX_TYPE.LDCOMPLEX;
			if (doubles > 0)
				return COMPLEX_TYPE.DCOMPLEX;
			return COMPLEX_TYPE.COMPLEX;
		} else if (imags > 0) {
			if (floats > 0)
				return COMPLEX_TYPE.FIMAGINARY;
			if (doubles > 0 && longs > 0)
				return COMPLEX_TYPE.LDIMAGINARY;
			if (doubles > 0)
				return COMPLEX_TYPE.DIMAGINARY;
			return COMPLEX_TYPE.IMAGINARY;
		} else if (doubles > 0) {
			return longs > 0 ? COMPLEX_TYPE.LDOUBLE :
				COMPLEX_TYPE.DOUBLE;
		} else if (floats > 0) {
			return COMPLEX_TYPE.FLOAT;
		} else if (chars > 0) {
			if (unsigneds > 0)
				return COMPLEX_TYPE.UCHAR;
			if (signeds > 0)
				return COMPLEX_TYPE.SCHAR;
			return COMPLEX_TYPE.CHAR;
		} else if (shorts > 0) {
			return unsigneds > 0 ? COMPLEX_TYPE.USHORT :
				COMPLEX_TYPE.SHORT;
		} else if (longs > 1) {
			return unsigneds > 0 ? COMPLEX_TYPE.ULONGLONG :
				COMPLEX_TYPE.LONGLONG;
		} else if (longs > 0) {
			return unsigneds > 0 ? COMPLEX_TYPE.ULONG :
				COMPLEX_TYPE.LONG;
		} else {
			if (unsigneds == 0 && signeds == 0 && ints == 0)
				throw new RuntimeException("invalid type: " +
					type);
			return unsigneds > 0 ? COMPLEX_TYPE.UINT :
				COMPLEX_TYPE.INT;
		}
	}

	public ComplexType(final List<BaseType.BASE_TYPE> type) {
		this.type = computeType(type);
		if (this.type == null)
			throw new RuntimeException("Invalid type: " + type);
	}

	public ComplexType(final COMPLEX_TYPE type) {
		this.type = type;
	}

	public COMPLEX_TYPE getType() {
		return type;
	}

	public String getTypeStr() {
		return types.getStr(type);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() +
			" (" + types.getStr(type) + ")";
	}
}
