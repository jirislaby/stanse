/*
 * Licensed under the GPLv2
 */

package cparser.rewriter;

import cparser.AST.BaseType;
import cparser.AST.ComplexType;
import cparser.AST.DeclarationSpecifiers;
import cparser.AST.Node;
import cparser.AST.StructOrUnion;
import cparser.AST.TypeOfSpecifier;
import cparser.AST.Typedef;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jiri Slaby
 */
public class ASTTypeSimplifier {
	public static void optimize(Node n) {
		for (Node child: n.getChildren()) {
			if (child instanceof DeclarationSpecifiers)
				simplifyTypes(child);
			else
				optimize(child);
		}
	}

	private static void simplifyTypes(final Node types) {
		final List<BaseType.BASE_TYPE> typeList =
			new LinkedList<BaseType.BASE_TYPE>();
		for (final Node type: types.getChildren()) {
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
		if (!typeList.isEmpty()) {
			types.clearChildren();
			final ComplexType ctype = new ComplexType(typeList);
			final DeclarationSpecifiers ds =
				(DeclarationSpecifiers)types;
			ds.setComplexType(ctype);
		}
	}
}
