package cz.muni.stanse.configuration;

import cz.muni.stanse.statistics.DummyEvaluationStatistic;
import cz.muni.stanse.statistics.EvaluationStatistic;
import cz.muni.stanse.configuration.source_enumeration.SourceCodeFilesException;
import cz.muni.stanse.configuration.source_enumeration.FileListEnumerator;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.checker.CheckingResult;
import cz.muni.stanse.checker.CheckingFailed;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerException;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.utils.Make;
import cz.muni.stanse.utils.ClassLogger;
import cz.muni.stanse.utils.msgformat.ColumnMessageFormatter;

import java.util.List;
import java.util.Vector;

public final class Configuration {

	// public section
	public Configuration() {
		sourceConfiguration = createDefaultSourceConfiguration();
		checkerConfigurations = createDefaultCheckerConfiguration();
	}

	public Configuration(final SourceConfiguration sourceConfiguration) {
		this.sourceConfiguration = sourceConfiguration;
		checkerConfigurations = createDefaultCheckerConfiguration();
	}

	public Configuration(final SourceConfiguration sourceConfiguration,
			final List<CheckerConfiguration> checkerConfiguration) {
		this.sourceConfiguration = sourceConfiguration;
		this.checkerConfigurations = checkerConfiguration;
	}

	public void evaluate(final CheckerErrorReceiver receiver,
			final CheckerProgressMonitor monitor,
			final EvaluationStatistic statistic) {
		final Vector<Integer> numCheckerConfigs = new Vector<Integer>();
		numCheckerConfigs.add(getCheckerConfigurations().size());
		int threadID = 0;
		for (final CheckerConfiguration checkerCfg : getCheckerConfigurations()) {
			new MonitoredThread(new MonitorForThread(++threadID, monitor)) {

				@Override
				public void run() {
					try {
						statistic.internalsStart();
						final LazyInternalStructures internals =
							checkerCfg.isInterprocedural()
							? getSourceConfiguration().getLazySourceInternals()
							: getSourceConfiguration().getLazySourceIntraproceduralInternals();
						statistic.internalsEnd();
						statistic.checkerStart(checkerCfg.getChecker().getName());
						final CheckingResult result =
							checkerCfg.getChecker().check(internals, receiver, getMonitor());
						statistic.checkerEnd(result);
					} catch (final CheckerException e) {
						statistic.checkerEnd(
							new CheckingFailed(e.getMessage(),
							getFailUnitName(checkerCfg)));
						ClassLogger.error(Configuration.class,
							"evaluate() failed :: when running configuration "
							+ checkerCfg.getCheckerClassName() + "arguments "
							+ checkerCfg.getCheckerArgumentsList() + " this "
							+ "exception arose:\n", e);
					} finally {
						synchronized (this.getClass()) {
							assert (numCheckerConfigs.get(0) > 0);
							numCheckerConfigs.set(0,
								numCheckerConfigs.firstElement() - 1);
							if (numCheckerConfigs.get(0) == 0) {
								receiver.onEnd();
							}
						}
					}
				}
			}.start();
		}
	}

	public void evaluateWait(final CheckerErrorReceiver receiver,
			final CheckerProgressMonitor monitor,
			final EvaluationStatistic statistic) {
		int threadID = 0;
		for (final CheckerConfiguration checkerCfg : getCheckerConfigurations()) {
			try {
				statistic.internalsStart();
				final LazyInternalStructures internals =
					checkerCfg.isInterprocedural()
					? getSourceConfiguration().getLazySourceInternals()
					: getSourceConfiguration().getLazySourceIntraproceduralInternals();
				statistic.internalsEnd();
				statistic.checkerStart(checkerCfg.getChecker().getName());
				final CheckingResult result =
					checkerCfg.getChecker().check(internals, receiver,
					new MonitorForThread(++threadID, monitor));
				statistic.checkerEnd(result);
			} catch (final CheckerException e) {
				statistic.checkerEnd(new CheckingFailed(e.getMessage(),
					getFailUnitName(checkerCfg)));
				ClassLogger.error(Configuration.class,
					"evaluateWait() failed :: when running configuration "
					+ checkerCfg.getCheckerClassName() + "arguments "
					+ checkerCfg.getCheckerArgumentsList() + " this "
					+ "exception arose:\n", e);
			}
		}
		receiver.onEnd();
	}

	@Deprecated
	public void evaluate_EachUnitSeparately(final CheckerErrorReceiver receiver,
			final CheckerProgressMonitor monitor) {
		evaluate_EachUnitSeparately(receiver, monitor,
			new DummyEvaluationStatistic());
	}

	@Deprecated
	public void evaluate_EachUnitSeparately(final CheckerErrorReceiver receiver,
			final CheckerProgressMonitor monitor,
			final EvaluationStatistic statistic) {
		new java.lang.Thread() {
			@Override
			public void run() {
				final CheckerErrorReceiver receiverWrapper =
					new CheckerErrorReceiver() {

						@Override
						public void receive(final CheckerError error) {
							receiver.receive(error);
						}
					};
				try {
					for (final String fileName : getSourceConfiguration().getSourceEnumerator().getSourceCodeFiles()) {
						monitor.write("<-> File: " + fileName + "\n");
						statistic.fileStart(fileName);
						new Configuration(
							new SourceConfiguration(new FileListEnumerator(
							Make.linkedList(fileName))),
							getCheckerConfigurations()).evaluateWait(receiverWrapper, monitor, statistic);
						statistic.fileEnd();
						monitor.write("<-> --------------------------------\n");
					}
				} catch (final SourceCodeFilesException e) {
					ClassLogger.error(Configuration.class,
						"evaluate_EachUnitSeparately() failed :: "
						+ "due to this exception:\n", e);
				}
				receiver.onEnd();
			}
		}.start();
	}

	@Deprecated
	public void evaluateWait_EachUnitSeparately(final CheckerErrorReceiver receiver,
			final CheckerProgressMonitor monitor,
			final EvaluationStatistic statistic) {
		final CheckerErrorReceiver receiverWrapper =
			new CheckerErrorReceiver() {
				@Override
				public void receive(final CheckerError error) {
					receiver.receive(error);
				}
			};
		try {
			for (final String fileName : getSourceConfiguration().getSourceEnumerator().getSourceCodeFiles()) {
				monitor.write("<-> File: " + fileName + "\n");
				statistic.fileStart(fileName);
				new Configuration(
					new SourceConfiguration(new FileListEnumerator(
					Make.linkedList(fileName))),
					getCheckerConfigurations()).evaluateWait(receiverWrapper, monitor, statistic);
				statistic.fileEnd();
				monitor.write("<-> --------------------------------\n");
			}
		} catch (final SourceCodeFilesException e) {
			ClassLogger.error(Configuration.class,
				"evaluateWait_EachUnitSeparately() failed :: "
				+ "due to this exception:\n", e);
		}
		receiver.onEnd();
	}

	public SourceConfiguration getSourceConfiguration() {
		return sourceConfiguration;
	}

	public List<CheckerConfiguration> getCheckerConfigurations() {
		return checkerConfigurations;
	}

	public static SourceConfiguration createDefaultSourceConfiguration() {
		return new SourceConfiguration(
			new FileListEnumerator(new Vector<String>()));
	}

	public static List<CheckerConfiguration> createDefaultCheckerConfiguration() {
		return Make.<CheckerConfiguration>linkedList();
	}

	// private section
	private final class MonitorForThread implements CheckerProgressMonitor {

		MonitorForThread(int threadID, final CheckerProgressMonitor monitor) {
			super();
			formatter = new ColumnMessageFormatter("<" + threadID + "> ", 1);
			this.monitor = monitor;
		}

		@Override
		public void write(final String s) {
			monitor.write(formatter.write(s + (s.endsWith("\n") ? "" : "\n")));
		}
		private final ColumnMessageFormatter formatter;
		private final CheckerProgressMonitor monitor;
	}

	private class MonitoredThread extends java.lang.Thread {

		MonitoredThread(final MonitorForThread monitor) {
			super();
			this.monitor = monitor;
		}

		final MonitorForThread getMonitor() {
			return monitor;
		}
		private final MonitorForThread monitor;
	}

	private String getFailUnitName(final CheckerConfiguration checkerCfg) {
		final LazyInternalStructures internals =
			checkerCfg.isInterprocedural()
			? getSourceConfiguration().getLazySourceInternals()
			: getSourceConfiguration().getLazySourceIntraproceduralInternals();
		return (internals.getUnits().size() == 1)
			? internals.getUnits().iterator().next().getName()
			: "<unknown>";
	}
	private final SourceConfiguration sourceConfiguration;
	private final List<CheckerConfiguration> checkerConfigurations;
}
