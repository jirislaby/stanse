package cz.muni.stanse.statistics;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;

import java.util.HashMap;
import java.util.Vector;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

public final class CheckerErrorsSorter {

    // public section

    public static void run(final Document database,
                           final java.util.Vector<String> ordering,
                           final String outFile) {
        System.out.print("Checker errors sorting\n"+
                         "~~~~~~~~~~~~~~~~~~~~~~\n\n");

        System.out.println("Not implemented yet!");

//        if (!StringToFileWriter.write(data,outFile))
//            return;
    }

    public static Vector<Pair<String,Vector<Element>>>
    splitByChecker(final List errElements) {
        return splitByLastOf("checker_name",errElements);
    }

    public static Vector<Pair<String,Vector<Element>>>
    splitByDescription(final List errElements) {
        return splitByLastOf("short_desc",errElements);
    }

    public static Vector<Pair<String,Vector<Element>>>
    splitByUnit(final List errElements) {
        return splitByLastOf("unit",errElements);
    }

    public static Triple<Vector<Element>,Vector<Element>,Vector<Element>>
    splitByResolved(final List errElements) {
        final Vector<Element> bugs = new Vector<Element>();
        final Vector<Element> falses = new Vector<Element>();
        final Vector<Element> untouched = new Vector<Element>();
        for (final Object obj : errElements)
            if (obj instanceof Element) {
                final Element elem = (Element)obj;
                if (elem.selectSingleNode(".//real-bug") != null)
                    bugs.add(elem);
                else if (elem.selectSingleNode(".//false-positive") != null)
                    falses.add(elem);
                else
                    untouched.add(elem);
            }
        return Triple.make(bugs,falses,untouched);
    }

    public static Vector<Element>
    filterByPresenceOf(final List errors, final String elemType) {
        final Vector<Element> result = new Vector<Element>();
        for (final Object obj : errors)
            if (obj instanceof Element) {
                final Element elem = (Element)obj;
                if (elem.selectSingleNode(".//" + elemType) != null)
                    result.add(elem);
            }
        return result;
    }

    // private section

    private static Vector<Pair<String,Vector<Element>>>
    splitByLastOf(final String childName, final List errElements) {
        final HashMap<String,Vector<Element>> perCheckerElemDict =
                new HashMap<String,Vector<Element>>();
        for (final Object obj : errElements)
            if (obj instanceof Element) {
                final List elems = ((Element)obj).selectNodes(".//"+childName);
                final String checkerName =
                        ((Element)elems.get(elems.size() - 1)).getText();
                if (!perCheckerElemDict.containsKey(checkerName))
                    perCheckerElemDict.put(checkerName,new Vector<Element>());
                perCheckerElemDict.get(checkerName).add((Element)obj);
            }

        final Vector<Pair<String,Vector<Element>>> result =
                new Vector<Pair<String,Vector<Element>>>();
        for (final String checkerName : perCheckerElemDict.keySet())
            result.add(Pair.make(checkerName,
                                 perCheckerElemDict.get(checkerName)));
        return result;
    }

    private CheckerErrorsSorter() {
    }
}
