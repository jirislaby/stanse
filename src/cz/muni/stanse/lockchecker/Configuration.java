package cz.muni.stanse.lockchecker;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import cz.muni.stanse.utils.xmlpatterns.XMLPattern;

/**
 * Class representing Lock Checker's configuration
 *
 * @author Radim Cebis
 */
class Configuration {

	private final Set<XMLPattern> locks = new HashSet<XMLPattern>();
	private final Set<XMLPattern> unlocks = new HashSet<XMLPattern>();
	private final Set<XMLPattern> assertLocked = new HashSet<XMLPattern>();
	private final Set<XMLPattern> assertUnlocked = new HashSet<XMLPattern>();
	private final Set<XMLPattern> skipOccurrences = new HashSet<XMLPattern>();
	private final Set<String> exprs = new HashSet<String>();
	private boolean countFlows = false;
	private boolean countSubvars = false;
	private boolean countFunctions = false;
	private boolean countPairs = false;
	private int threshold = -10000;
	private boolean onlyTopFunctions = false;
	private boolean generateDoubleErrors = false;
	private boolean generateMoreLocksErrors = false;

	/**
	 * Create the configuration from the file.
	 *
	 * @param file contains the configuration specification
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public Configuration(File file) throws DocumentException {
		final Document configDocument = (new org.dom4j.io.SAXReader()).
			read(file);
		final Element rootElement = configDocument.getRootElement();
		List<Element> elements;
		Element element;

		elements = rootElement.selectNodes("patterns/pattern[@name=\"lock\"]");
		for (final Element el : elements)
			locks.add(new XMLPattern(el));
		elements = rootElement.selectNodes("patterns/pattern[@name=\"unlock\"]");
		for (final Element el : elements)
			unlocks.add(new XMLPattern(el));
		elements = rootElement.selectNodes("patterns/pattern[@name=\"assertLocked\"]");
		for (final Element el : elements)
			assertLocked.add(new XMLPattern(el));
		elements = rootElement.selectNodes("patterns/pattern[@name=\"assertUnlocked\"]");
		for (final Element el : elements)
			assertUnlocked.add(new XMLPattern(el));
		elements = rootElement.selectNodes("patterns/pattern[@name=\"skipOccurrence\"]");
		for (final Element el : elements)
			skipOccurrences.add(new XMLPattern(el));
		elements = rootElement.selectNodes("expressions/expression");
		for (final Element el : elements)
			exprs.add(el.getText());

		element = (Element)rootElement.selectSingleNode("countFlows");
		countFlows = element.getText().equals("1");
		element = (Element)rootElement.selectSingleNode("countSubvars");
		countSubvars = element.getText().equals("1");
		element = (Element)rootElement.selectSingleNode("countPairs");
		countPairs = element.getText().equals("1");
		element = (Element)rootElement.selectSingleNode("countFunctions");
		countFunctions = element.getText().equals("1");
		element = (Element)rootElement.selectSingleNode("onlyTopFunctions");
		onlyTopFunctions = element.getText().equals("1");
		element = (Element)rootElement.selectSingleNode("generateDoubleErrors");
		generateDoubleErrors = element.getText().equals("1");
		element = (Element)rootElement.selectSingleNode("generateMoreLocksErrors");
		generateMoreLocksErrors = element.getText().equals("1");
		element = (Element)rootElement.selectSingleNode("threshold");
		threshold = Integer.valueOf(element.getText());
	}

	/**
	 * @return assert locked patterns
	 */
	public Set<XMLPattern> getAssertLocked() {
		return assertLocked;
	}

	/**
	 * @return assert unlocked patterns
	 */
	public Set<XMLPattern> getAssertUnlocked() {
		return assertUnlocked;
	}

	/**
	 * @return lock patterns
	 */
	public Set<XMLPattern> getLocks() {
		return locks;
	}

	/**
	 * @return unlock patterns
	 */
	public Set<XMLPattern> getUnlocks() {
		return unlocks;
	}

	/**
	 * @return skip occurrences patterns
	 */
	public Set<XMLPattern> getSkipOccurrences() {
		return skipOccurrences;
	}

	/**
	 * @return true if we should count flows, otherwise only occurrences are counted
	 */
	public boolean countFlows() {
		return countFlows;
	}

	/**
	 * @return true if we should count sub-variables
	 */
	public boolean countSubvars() {
		return countSubvars;
	}

	/**
	 * @return expressions which should be considered as variable identifiers
	 */
	public Set<String> getExprs() {
		return exprs;
	}

	/**
	 * @return true if we should count function identifiers
	 */
	public boolean countFunctions() {
		return countFunctions;
	}

	/**
	 * @return true if we should count z-statistic using pairs
	 */
	public boolean countPairs() {
		return countPairs;
	}

	/**
	 * @return threshold - minimal importance value for possible locking errors
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * @return true if we should generate errors only for top functions
	 */
	public boolean onlyTopFunctions() {
		return onlyTopFunctions;
	}

	/**
	 * @return true if we should generate errors for double lock/unlock errors
	 */
	public boolean generateDoubleErrors() {
		return generateDoubleErrors;
	}

	/**
	 * @return true if we should generate errors for states which have more locks held than in common state
	 */
	public boolean generateMoreLocksErrors() {
		return generateMoreLocksErrors;
	}
}
