package cz.muni.stanse.checker;

import java.util.Collection;

public class CheckerErrorReceiver {

	public void receiveAll(final Collection<CheckerError> errors) {
		for (final CheckerError error : errors)
			receive(error);
	}

	public void receive(final CheckerError error) {
	}

	public void onEnd() {
	}
}
