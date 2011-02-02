/*
 * Licensed under the GPLv2
 */

package cparser.AST;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

/**
 *
 * @author Jiri Slaby
 */
public class NodeCreator {
	private static final Logger logger =
		Logger.getLogger(NodeCreator.class);
	private final static String pkg =
		NodeCreator.class.getPackage().getName() + ".";

	public static Node create(final String name, final Class[] type,
			final Object[] arg) {
		try {
			final Class cls = Class.forName(pkg + name);
			final Constructor<Node> ct = cls.getConstructor(type);
			return ct.newInstance(arg);
		} catch (ClassNotFoundException e) {
			logger.error(e);
		} catch (NoSuchMethodException e) {
			logger.error(e);
		} catch (InstantiationException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e.getCause());
		}
		throw new RuntimeException("an error occurred");
	}
}
