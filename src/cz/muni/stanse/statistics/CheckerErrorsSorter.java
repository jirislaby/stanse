package cz.muni.stanse.statistics;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;

import java.util.HashMap;
import java.util.Vector;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;

public final class CheckerErrorsSorter {

    // public section

    public static void run(final Document database,
                           final Vector<String> ordering,
                           final String outDir) {
        System.out.print("Checker errors sorting\n"+
                         "~~~~~~~~~~~~~~~~~~~~~~\n\n");

        System.out.println("(1/2) Collecting error nodes...");
        final Vector<Element> errors =
            new Vector<Element>(database.selectNodes("database/errors/error"));
        System.out.println("      Done.");

        System.out.println("(2/2) Sorting and writting results...");
        processPhases(errors,ordering,outDir);
        System.out.println("      Done.");
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

    public static Vector<Pair<String,Vector<Element>>>
    splitByImportance(final List errElements) {
        return splitByLastOf("importance",errElements);
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
        final HashMap<String,Vector<Element>> namesDict =
                new HashMap<String,Vector<Element>>();
        for (final Object obj : errElements)
            if (obj instanceof Element) {
                final List elems = ((Element)obj).selectNodes(".//"+childName);
                final String name =
                        ((Element)elems.get(elems.size() - 1)).getText();
                if (!namesDict.containsKey(name))
                    namesDict.put(name,new Vector<Element>());
                namesDict.get(name).add((Element)obj);
            }

        final Vector<Pair<String,Vector<Element>>> result =
                new Vector<Pair<String,Vector<Element>>>();
        for (final String name : namesDict.keySet())
            result.add(Pair.make(name,namesDict.get(name)));
        return result;
    }

    private static void processPhases(final Vector<Element> elements,
                                      final Vector<String> ordering,
                                      final String outDir){
        if (ordering.isEmpty()) {
            final String outFile = outDir + "/data.xml";
            System.out.print("      Storing data into '" + outFile + "'...");
            writeToXML(elements,outFile);
            System.out.println("Done.");
            return;
        }

        final String order = ordering.get(0);

        String infoText;
        Vector<Pair<String,Vector<Element>>> split;
        if (order.equals("checker_name")) {
            infoText = "Sorted by 'checker_name'.";
            split = splitByChecker(elements);
        }
        else if (order.equals("description")) {
            infoText = "Sorted by 'description'.";
            split = splitByDescription(elements);
        }
        else if (order.equals("unit")) {
            infoText = "Sorted by 'unit'.";
            split = splitByUnit(elements);
        }
        else if (order.equals("importance")) {
            infoText = "Sorted by 'importance'.";
            split = splitByImportance(elements);
        }
        else {
            System.out.println("    *** ERROR: keyword '" + order + "' is" +
                               "not supported. Phase has FAILED!");

            infoText = "ERROR: *** ERROR: keyword '" + order + "' is" +
                       "not supported.";
            split = new Vector<Pair<String,Vector<Element>>>();
        }

        final String infoFile = outDir + "/info.txt";
        System.out.print("      Storing dir-info into '" + infoFile + "'...");
        StringToFileWriter.write(infoText,infoFile);
        System.out.println("Done.");

        final Vector<String> subOrdering =
                new Vector<String>(ordering.subList(1,ordering.size()));

        int dirCounter = 0;
        for (final Pair<String,Vector<Element>> item : split)
            processPhases(item.getSecond(),subOrdering,
                          outDir + "/" + (++dirCounter));
    }

    private static void writeToXML(final Vector<Element> elements,
                                   final String outFile) {
        final Document doc = DocumentHelper.createDocument();
        final Element db = doc.addElement("database");
        db.addElement("files");
        db.addElement("internals");
        db.addElement("checkers");
        db.addElement("checkfails");
        final Element errElem = db.addElement("errors");
        for (final Element elem : elements)
            errElem.add(elem.createCopy());
        DocumentToFileWriter.write(doc,outFile);
    }

    private CheckerErrorsSorter() {
    }
}
