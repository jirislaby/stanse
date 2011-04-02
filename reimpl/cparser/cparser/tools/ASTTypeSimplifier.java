/*
 * Licensed under the GPLv2
 */

package cparser.tools;

import cparser.AST.BaseType;
import cparser.AST.ComplexType;
import cparser.AST.Node;
import cparser.AST.StructOrUnion;
import cparser.AST.TypeOfSpecifier;
import cparser.AST.Typedef;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jiri Slaby
 */
public class ASTTypeSimplifier {
	public static ComplexType simplifyTypes(final Collection<Node> types) {
		final List<BaseType.BASE_TYPE> typeList =
			new LinkedList<BaseType.BASE_TYPE>();
		for (final Node type: types) {
			if (type instanceof BaseType) {
				final BaseType bt = (BaseType)type;
				typeList.add(bt.getType());
				continue;
			} else if (type instanceof StructOrUnion) {
				continue;
			} else if (type instanceof cparser.AST.Enum) {
				continue;
			} else if (type instanceof Typedef) {
				continue;
			} else if (type instanceof TypeOfSpecifier) {
				continue;
			}
			throw new RuntimeException("Unknown type: " + type);
		}
		if (!typeList.isEmpty())
			return new ComplexType(typeList);
		return null;
	}
}
