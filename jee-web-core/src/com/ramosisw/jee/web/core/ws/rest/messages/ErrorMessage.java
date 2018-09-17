package com.ramosisw.jee.web.core.ws.rest.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase para representar errores en RESTful
 * 
 * El cliente podria recibir informacion como esta: <b>JSON</b>
 * 
 * <pre>
 * {
 *	"code" : 666,
 *	"message" : "Ocurrion un error interno
 * }
 * </pre>
 * 
 * @author jcramos
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorMessage extends BaseMessage {
	public ErrorMessage() {
		super();
	}

	public ErrorMessage(int code, String message) {
		super(code, message);
	}
}
