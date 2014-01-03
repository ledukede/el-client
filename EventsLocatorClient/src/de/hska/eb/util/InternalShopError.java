package de.hska.eb.util;

public class InternalShopError extends RuntimeException {
	private static final long serialVersionUID = 3540792704055189581L;

	public InternalShopError(String msg) {
		super(msg);
	}
	
	public InternalShopError(String msg, Throwable t) {
		super(msg, t);
	}
}