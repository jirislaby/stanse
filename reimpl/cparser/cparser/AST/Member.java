/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class Member extends Node {
	final String member;

	private Member() { throw new UnsupportedOperationException(); }

	public Member(final String member) {
		this.member = member;
	}

	@Override
	void XMLChildren(final StringBuilder sb) {
		sb.append(member);
	}
}
