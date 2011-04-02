/*
 * Licensed under GPLv2
 */

package cz.muni.stanse.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * A class providing Time measuring and management
 *
 * @author Jiri Slaby
 */
public class TimeManager {
	private long startTime = 0;
	private boolean cpuSupp;
	private ThreadMXBean bean;

	public TimeManager() {
		bean = ManagementFactory.getThreadMXBean();
		cpuSupp = bean.isCurrentThreadCpuTimeSupported();
	}

	/**
	 * Reference point to start the measurement
	 */
	public void measureStart() {
		startTime = getCurrentTime();
	}

	/**
	 * Get (possibly) CPU time since measureStart
	 *
	 * @return elapsed time in ms since measureStart
	 */
	public long measureElapsedMs() {
		return measureElapsed() / 1000000L;
	}

	/**
	 * Get (possibly) CPU time since measureStart
	 *
	 * @return elapsed time in ns since measureStart
	 */
	public long measureElapsed() {
		return getCurrentTime() - startTime;
	}

	private long getCurrentTime() {
		return cpuSupp ? bean.getCurrentThreadCpuTime() :
			System.currentTimeMillis() * 1000000L;
	}
}