/*
 * Copyright (c) 2009 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import org.dom4j.Document;

import java.util.Collection;

/**
 * Takes care of unit dropping when there are too many of them.
 */
public interface UnitManager {
    public Collection<CFGHandle> getCFGHandles(Unit unit);
    public Document getXMLDocument(Unit unit);
    public Unit touchUnit(Unit unit);
    public String getUnitName(CFGHandle cfg);
}
