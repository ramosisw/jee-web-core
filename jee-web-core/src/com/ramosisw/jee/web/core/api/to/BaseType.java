package com.ramosisw.jee.web.core.api.to;

/**
 * 
 * @author jcramos
 *
 */
public class BaseType {
	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public BaseType() {
	}

	/**
	 * 
	 * @param code
	 *            Codigo del mensaje
	 * @param message
	 *            Mensaje
	 */
	public BaseType(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseType [code=");
		builder.append(code);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
