package com.ramosisw.jee.web.core.api.to;

/**
 * Clase utilizada para respuesta de mensajes basicos donde se incluya un codigo del mensaje y el mensaje
 * @author jcramos
 *
 */
public class BasicType extends BaseType {

	public BasicType() {
		super();
	}

	public BasicType(int code, String message) {
		super(code, message);
	}

}
