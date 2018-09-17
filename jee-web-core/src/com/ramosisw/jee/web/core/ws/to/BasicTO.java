package com.ramosisw.jee.web.core.ws.to;

import com.ramosisw.jee.web.core.api.to.BasicType;
import com.ramosisw.jee.web.core.ws.rest.messages.BasicMessage;

/**
 * Clase para transportar de objetos de tipo BasicType a BasicMessage y
 * viceversa
 * 
 * @author jcramos
 *
 */
public class BasicTO {
	/**
	 * Transporta el objeto
	 * 
	 * @param from
	 *            desde donde se transportara
	 * @return objeto transportado
	 */
	public static BasicMessage getTO(BasicType from) {
		BasicMessage to = new BasicMessage();
		to.setCode(from.getCode());
		to.setMessage(from.getMessage());
		return to;
	}

	/**
	 * 
	 * @param from
	 *            desde donde se transportara
	 * @return objeto transportado
	 */
	public static BasicType getTO(BasicMessage from) {
		BasicType to = new BasicType();
		to.setCode(from.getCode());
		to.setMessage(from.getMessage());
		return to;
	}
}
