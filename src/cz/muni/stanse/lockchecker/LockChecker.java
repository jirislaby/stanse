package cz.muni.stanse.lockchecker;

import java.util.Collection;
import org.apache.log4j.Logger;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerException;
import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.checker.CheckingResult;
import cz.muni.stanse.checker.CheckingSuccess;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.LazyInternalStructures;

/**
 * Lock Checker class used to find possible locking errors
 *
 *
 * @author Radim Cebis
 *
 */
public class LockChecker extends Checker {
	private Configuration conf;
	private final static Logger logger =
		Logger.getLogger(LockChecker.class.getName());

	/**
	 * Constructs the Lock Checker using given configuration
	 * @param conf Configuration
	 */
	LockChecker(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public CheckingResult check(final LazyInternalStructures internals,
		final CheckerErrorReceiver errReceiver,
		final CheckerProgressMonitor monitor) throws CheckerException {
		final SummariesBuilder summariesBuilder = new SummariesBuilder(
			internals.getNodeToCFGdictionary(),
			internals.getArgumentPassingManager(), conf);

		for (CFGHandle handle : internals.getStartFunctions()) {
			summariesBuilder.traverse(handle.getStartNode(),
				internals.getNavigator(), new State());
		}

		final Summaries sum = summariesBuilder.getSummaries();
		final CheckerErrorFilter filter = new CheckerErrorFilter();

		if (conf.onlyTopFunctions()) {
			for (final CFGHandle cfgh :
					internals.getStartFunctions()) {
				final FunctionSummary fs =
					sum.get(cfgh.getStartNode());
				checkFunctions(fs.getFunctionStateSummaries(),
					errReceiver, filter);
			}
		} else {
			checkFunctions(sum.getAllFunctionStateSummaries(),
				errReceiver, filter);
		}

		filter.generateErrors(errReceiver);

		logger.debug(sum);

		return new CheckingSuccess();
	}

	private void checkFunctions(final Collection<FunctionStateSummary> sums,
			final CheckerErrorReceiver errReceiver,
			final CheckerErrorFilter filter) {
		for (final FunctionStateSummary fss : sums) {
			final ErrorGenerator generator = new ErrorGenerator(fss,
				conf.countFlows(), conf.countPairs(),
				conf.getThreshold(),
				conf.generateMoreLocksErrors());
			generator.generateErrors(filter);
			if (conf.generateDoubleErrors())
				fss.getErrHolder().save(errReceiver);
		}
	}

	@Override
	public String getName() {
		return "Lock Checker for finding variables which should be " +
			" locked but are not";
	}
}
