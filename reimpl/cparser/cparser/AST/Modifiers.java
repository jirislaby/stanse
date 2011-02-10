/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.Table;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiri Slaby
 */
public class Modifiers {
	public enum SC {
		NONE,
		TYPEDEF,
		EXTERN,
		STATIC,
		REGISTER,
		THREAD,
		SC_SIZE
	}
	public enum TQ {
		CONST,
		RESTRICT,
		VOLATILE,
		TQ_SIZE
	}
	private static Table<SC> SCs;
	private static Table<TQ> TQs;

	static {
		SCs = new Table<SC>(SC.SC_SIZE.ordinal());
		SCs.fill("typedef", SC.TYPEDEF);
		SCs.fill("extern", SC.EXTERN);
		SCs.fill("static", SC.STATIC);
		SCs.fill("register", SC.REGISTER);
		SCs.fill("__thread", SC.THREAD);

		TQs = new Table<TQ>(TQ.TQ_SIZE.ordinal());
		TQs.fill("const", TQ.CONST);
		TQs.fill("restrict", TQ.RESTRICT);
		TQs.fill("volatile", TQ.VOLATILE);
	};

	private SC sc = SC.NONE;
	private boolean tq[] = new boolean[TQ.TQ_SIZE.ordinal()];
	private boolean fsInline = false;

	public void setSC(final SC sc) {
		this.sc = sc;
	}

	public boolean setSC(final String sc) {
		final SC s = SCs.getVal(sc);
		if (s == null)
			return false;
		setSC(s);
		return true;
	}

	public boolean hasSC() {
		return sc != SC.NONE;
	}

	public String getSCString() {
		return SCs.getStr(sc);
	}

	public boolean setFS(final String fs) {
		if (!fs.equals("inline"))
			return false;
		fsInline = true;
		return true;
	}

	public boolean hasFS() {
		return fsInline;
	}

	public String getFSString() {
		return "inline";
	}

	public void setTQ(final TQ tq) {
		this.tq[tq.ordinal()] = true;
	}

	public boolean setTQ(final String tq) {
		final TQ t = TQs.getVal(tq);
		if (t == null)
			return false;
		setTQ(t);
		return true;
	}

	public boolean hasTQ(TQ tq) {
		return this.tq[tq.ordinal()];
	}

	public List<String> getTQList() {
		final List<String> ret = new LinkedList<String>();
		for (int a = 0; a < TQ.TQ_SIZE.ordinal(); a++)
			if (tq[a])
				ret.add(TQs.getStr(a));
		return ret;
	}
}
