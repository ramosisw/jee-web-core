package com.ramosisw.jee.web.core.pl.ex;

/**
 * 
 * @author jcramos
 *
 */
public class MultipleRecordsFoundException extends DAOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8547577280495644361L;

	public MultipleRecordsFoundException() {
	}

	public MultipleRecordsFoundException(String message) {
		super(message);
	}

	public MultipleRecordsFoundException(Throwable cause) {
		super(cause);
	}

	public MultipleRecordsFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MultipleRecordsFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
