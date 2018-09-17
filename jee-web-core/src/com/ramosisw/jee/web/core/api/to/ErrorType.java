package com.ramosisw.jee.web.core.api.to;

/**
 * Clase para el manejo de errores
 * 
 * @author jcramos
 *
 */
public class ErrorType extends BaseType {

	public ErrorType() {
		super();
	}

	public ErrorType(int code, String message) {
		super(code, message);
	}

}
