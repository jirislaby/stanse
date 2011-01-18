package cz.muni.stanse.lockchecker;

import java.util.HashMap;
import java.util.Map;

/**
 * Repository for unmodifiable states. Used to save memory and insure the
 * immutability of the states used in summaries.
 *
 * @author Radim Cebis
 */
class StateRepository {
	private static class UnmodifiableState extends State {
		@Override
		public boolean propagate(State from) {
			throw new UnsupportedOperationException("State is unmodifiable");
		}

		public UnmodifiableState(State state) {
			super(state);
		}
	}

	private Map<State,State> repos = new HashMap<State, State>();

	/**
	 * Get unmodifiable state from this repository for specified state
	 * @param state specified state
	 * @return unmodifiable state from this repository
	 */
	public State get(State state) {
		State res = repos.get(state);
		if (res == null) {
			res = new UnmodifiableState(state);
			repos.put(res, res);
		}
		return res;
	}
}
