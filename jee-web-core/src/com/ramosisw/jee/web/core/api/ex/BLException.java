package com.ramosisw.jee.web.core.api.ex;

import com.ramosisw.jee.web.core.api.to.ErrorType;

/**
 * Clase para el control de excepciones de las reglas de negocio
 * 
 * @author jcramos
 *
 */
public class BLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4228729969418975118L;

	private ErrorType error;

	public BLException() {
	}

	public BLException(int code, String message) {
		super(message);
		error = new ErrorType(code, message);
	}

	public BLException(ErrorType error) {
		super(error != null ? error.getMessage() : "Message not found");
		setError(error);
	}

	public BLException(String message) {
		super(message);
	}

	public BLException(String message, Object... format) {
		super(String.format(message, format));
	}

	public BLException(Throwable cause) {
		super(cause);
	}

	public BLException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @return the error
	 */
	public ErrorType getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(ErrorType error) {
		this.error = error;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BLException [");
		if (error != null) {
			builder.append("error=");
			builder.append(error);
		}
		builder.append("]");
		return builder.toString();
	}

}
