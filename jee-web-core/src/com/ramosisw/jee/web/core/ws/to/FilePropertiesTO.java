package com.ramosisw.jee.web.core.ws.to;

import com.ramosisw.jee.web.core.api.to.FilePropertiesType;
import com.ramosisw.jee.web.core.ws.rest.messages.FilePropertiesMessage;

/**
 * Clase transportadora de objetos Convierte de FilePropertiesMessage a
 * FilePropertiesType o viceversa
 * 
 * @author jcramos
 *
 */
public class FilePropertiesTO {
	/**
	 * Transporta de FilePropertiesMessage a FilePropertiesType
	 * 
	 * @param from
	 *            desde donde transportaremos
	 * @return objeto transportardo
	 */
	public static FilePropertiesType getTO(FilePropertiesMessage from) {
		FilePropertiesType to = new FilePropertiesType();
		to.setCheckSum(from.getCheckSum());
		to.setCompressionType(from.getCompressionType());
		to.setContent(from.getContent());
		to.setEncodingType(from.getEncodingType());
		to.setFileName(from.getFileName());
		to.setSize(from.getSize());
		return to;
	}

	/**
	 * Transporta de FilePropertiesType a FilePropertiesMessage
	 * 
	 * @param from
	 *            desde donde transportaremos
	 * @return objeto transportardo
	 */
	public static FilePropertiesMessage getTO(FilePropertiesType from) {
		FilePropertiesMessage to = new FilePropertiesMessage();
		to.setCheckSum(from.getCheckSum());
		to.setCompressionType(from.getCompressionType());
		to.setContent(from.getContent());
		to.setEncodingType(from.getEncodingType());
		to.setFileName(from.getFileName());
		to.setSize(from.getSize());
		return to;
	}
}
