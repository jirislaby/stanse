package cz.muni.stanse.lockchecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.dom4j.Element;

import cz.muni.stanse.codestructures.PassingSolver;
import cz.muni.stanse.utils.Pair;

/**
 * Class representing ID transformations
 * @author Radim Cebis
 *
 */
class VarTransformations {
	
	private static class EntryComparator implements Comparator<Pair<String, String>>, Serializable {
		
		private static final long serialVersionUID = 1L;

		// longest identificators first
		@Override
		public int compare(Pair<String, String> o1, Pair<String, String> o2) {
			return o2.getFirst().length() - o1.getFirst().length();
		}
		
		
	}
	
	private SortedSet<Pair<String, String>> fromCalleeToCaller = new TreeSet<Pair<String,String>>(new EntryComparator()); 
	private SortedSet<Pair<String, String>> fromCallerToCallee = new TreeSet<Pair<String,String>>(new EntryComparator());
	
	/**
	 * Save new transformation
	 * @param idInCalle ID in callee
	 * @param idInCaller ID in caller
	 */
	public void addTransformation(String idInCalle, String idInCaller) {
		fromCalleeToCaller.add(new Pair<String,String>(idInCalle, idInCaller));
		fromCallerToCallee.add(new Pair<String,String>(idInCaller, idInCalle));
	}
		
	/**
	 * Transform ID
	 * @param name name of the variable to be transformed
	 * @param fromCallerToCallee should it be transformed from caller to callee? Otherwise name is transformed form callee to caller
	 * @return transformed name or null if there was nothing to transform
	 */
	public String transform(String name, boolean fromCallerToCallee) {
		SortedSet<Pair<String, String>> set;
		String oldName = name;	
		if(fromCallerToCallee) {
			 set = this.fromCallerToCallee;
			for(Pair<String, String> entry: set) {
				if(name.contains(entry.getFirst())) {
					int position = name.indexOf(entry.getFirst());
					// if there is some other name before the key than it is not the same variable...
					if(!containsName(name.substring(0, position)) 
							&& (name.length() == position + entry.getFirst().length()
								|| name.charAt(position + entry.getFirst().length()) == ' ' )) {
						// inject the variable inside the variable
						name = name.replaceFirst(Pattern.quote(entry.getFirst()), entry.getSecond());
						break;
					}
				}
			}
		} else {
			set = this.fromCalleeToCaller;
			for(Pair<String, String> entry: set) {
				if(name.contains(entry.getFirst())) {
					int position = name.indexOf(entry.getFirst());
					// if there is some other name before the key than it is not the same variable...
					if(!containsName(name.substring(0, position)) 
							&& (name.length() == position + entry.getFirst().length()
								|| name.charAt(position + entry.getFirst().length()) == ' ' )) {
						
						int firstLetter = firstLetter(entry.getSecond());
						// inject the variable operators outside
						name = name.replaceFirst(Pattern.quote(entry.getFirst()), entry.getSecond().substring(firstLetter));
						name = entry.getSecond().substring(0, firstLetter) + name;
						break;
					}
				}
			}
		}
		
		if(!oldName.equals(name)) return name;
		else return null;
	}
	
	/**
	 * returns position of first letter
	 * @param name
	 * @return
	 */
	private int firstLetter(String name) {		
		int res = 0;
		for(; res < name.length(); res++) {
			if(Character.isLetter(name.charAt(res)))
				return res;
		}
		return 0;
		
	}

	private boolean containsName(String name) {
		String[] split = name.split(" ");
		for(int i = 0; i < split.length; i++) {
			String str = split[i];
			if(str.equals("&") || str.equals(".") || str.equals("*") || str.equals("[]") || str.equals("")) {
				continue;
			} else {
				return true;
			}			
		}
		return false;
	}

	/**
	 * 
	 * @param name
	 * @return name of the variable which is accessed here (for example &ds->dd_lock returns ds)
	 */	
	private static String getName(String name, boolean fullName) {
		String[] split = name.split(" ");
		String result = null;
		for(int i = 0; i < split.length; i++) {
			String str = split[i];
			if(str.equals("&") || str.equals(".") || str.equals("*") || str.equals("[]")) {
				continue;
			} else {
				if(fullName) {
					StringBuilder sb = new StringBuilder(str);
					for(int j = i+1; j < split.length; j++) {
						sb.append(" ");
						sb.append(split[j]);
					}
					return sb.toString();
				} else {
					return str;
				}
			}			
		}
		return result;
	}

	/**
	 * Create argument ID from element
	 * @param el
	 * @return argument ID
	 */
	public static String makeArgument(Element el) {
		return PassingSolver.makeArgument(el);
	}
	
	public static String prettyPrint(String id) {
		List<String> operators = new ArrayList<String>();
		String oldId = id;
		boolean done = false;
		List<String> fullName = new ArrayList<String>(Arrays.asList(getName(id, true).split(" ")));
		while(!done) {
			if(id.startsWith(". *")) { 
				operators.add("->");
				id = id.substring(4);
			} else if(id.startsWith("&")) { 
				operators.add("&");
				id = id.substring(2);
			} else if(id.startsWith(".")) { 
				operators.add(".");
				id = id.substring(2);
			} else if(id.startsWith("*")) { 
				operators.add("*");
				id = id.substring(2);
			} else if(id.startsWith("[]")) { 
				operators.add("[]");
				id = id.substring(3);
			} else {
				done = true;
			}
		}		
		String result = fullName.get(0);
		fullName.remove(0);
		//Collections.reverse(operators);
		try {
			for(String operator : operators) {
				Iterator<String> iterator = fullName.iterator();
				if(operator.equals("&")) {	
					result = "&" + result + "";
				} else if(operator.equals("->")) {
					String str = iterator.next();
					result += "->" + str;
					iterator.remove();
				} else if(operator.equals(".")) {
					String str = iterator.next();
					result += "." + str;
					iterator.remove();
				} else if(operator.equals("*")) {
					result = "*" + result;
				} else if(operator.equals("[]")) {
					result = "[" + result + "]";
				}			
			}
		} catch (NoSuchElementException ex) {
			// conversion failed, return just old id
			return oldId;
		}
		// if we fail to convert to pretty print, use at least old id
		if(result.length()==0)result = oldId;		
		return result;
	}	
}
