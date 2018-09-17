package com.ramosisw.jee.web.core.api.ex;

import com.ramosisw.jee.web.core.api.to.ErrorType;

/**
 * Clase para el control de excepciones del acceso a datos
 * @author jcramos
 *
 */
public class DLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1466507932633204303L;

	private ErrorType error;

	public DLException() {
	}
	
	public DLException(int code, String message) {
		super(message);
		error = new ErrorType(code, message);
	}

	public DLException(ErrorType error) {
		setError(error);
	}

	public DLException(ErrorType error, String message) {
		super(message);
		setError(error);
	}

	public DLException(String message) {
		super(message);
	}

	public DLException(String message, Object... format) {
		super(String.format(message, format));
	}

	public DLException(Throwable cause) {
		super(cause);
	}

	public DLException(String message, Throwable cause) {
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
		builder.append("DLException [");
		if (error != null) {
			builder.append("error=");
			builder.append(error);
		}
		builder.append("]");
		return builder.toString();
	}

}
