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
		List<Element> elements;

		final Document configDocument = (new org.dom4j.io.SAXReader()).
			read(file);
		final Element rootElement = configDocument.getRootElement();

		elements = rootElement.selectNodes("patterns/pattern[@name=\"lock\"]");
		for(Element el : elements)
			locks.add(new XMLPattern(el.asXML()));
		elements = rootElement.selectNodes("patterns/pattern[@name=\"unlock\"]");
		for(Element el : elements)
			unlocks.add(new XMLPattern(el.asXML()));
		elements = rootElement.selectNodes("patterns/pattern[@name=\"assertLocked\"]");
		for (Element el : elements)
			assertLocked.add(new XMLPattern(el.asXML()));
		elements = rootElement.selectNodes("patterns/pattern[@name=\"assertUnlocked\"]");
		for (Element el : elements)
			assertUnlocked.add(new XMLPattern(el.asXML()));
		elements = rootElement.selectNodes("patterns/pattern[@name=\"skipOccurrence\"]");
		for (Element el : elements)
			skipOccurrences.add(new XMLPattern(el.asXML()));
		elements = rootElement.selectNodes("expressions/expression");
		for (Element el : elements)
			exprs.add(el.getText());

		elements = rootElement.selectNodes("countFlows");
		for(Element el : elements) {
			countFlows = el.getText().equals("1");
		}
		elements = rootElement.selectNodes("countSubvars");
		for(Element el : elements) {
			countSubvars = el.getText().equals("1");
		}
		elements = rootElement.selectNodes("countPairs");
		for(Element el : elements) {
			countPairs = el.getText().equals("1");
		}
		elements = rootElement.selectNodes("countFunctions");
		for(Element el : elements) {
			countFunctions = el.getText().equals("1");
		}
		elements = rootElement.selectNodes("onlyTopFunctions");
		for(Element el : elements) {
			onlyTopFunctions = el.getText().equals("1");
		}
		elements = rootElement.selectNodes("generateDoubleErrors");
		for(Element el : elements) {
			generateDoubleErrors = el.getText().equals("1");
		}
		elements = rootElement.selectNodes("generateMoreLocksErrors");
		for(Element el : elements) {
			generateMoreLocksErrors = el.getText().equals("1");
		}
		elements = rootElement.selectNodes("threshold");
		for(Element el : elements) {
			threshold = Integer.valueOf(el.getText());
		}
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
