package de.hska.eb.util;

public class InternalEventslocatorError extends RuntimeException {
	private static final long serialVersionUID = 3540792704055189581L;

	public InternalEventslocatorError(String msg) {
		super(msg);
	}
	
	public InternalEventslocatorError(String msg, Throwable t) {
		super(msg, t);
	}
}