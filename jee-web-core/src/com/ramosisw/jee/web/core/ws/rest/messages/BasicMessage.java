package com.ramosisw.jee.web.core.ws.rest.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase para representar mensajes basicos en RESTful
 * 
 * El cliente podria recibir informacion como esta: <b>JSON</b>
 * 
 * <pre>
 * {
 *	"code" : 200,
 *	"message" : "Mensaje"
 * }
 * </pre>
 * 
 * @author jcramos
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BasicMessage extends BaseMessage {

	public BasicMessage() {
		super();
	}

	public BasicMessage(int code, String message) {
		super(code, message);
	}

}
