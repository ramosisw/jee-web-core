package com.ramosisw.jee.web.core.api.util;

import com.ramosisw.jee.web.core.api.ex.BLException;

public class EJBUtils {
	/**
	 * Obtiene la instancia de acceso a datos a partir de un EJB
	 * 
	 * @param <T>
	 *            tipo de la instancia <code>bean</code>
	 * @param bean
	 *            EJB del cual se quiere obtener la instancia
	 * @param className
	 *            nombre de la clase larga del EJB
	 * @param errorCode
	 *            Codigo del error
	 * @param formatMessage
	 *            mensaje de excepcion
	 * 
	 * @return instancia EJB
	 * @throws BLException
	 *             en caso de no esta inicializado el EJB
	 */
	protected static <T> T getBean(T bean, String className, int errorCode, String formatErrorMessage)
			throws BLException {
		if (bean == null)
			throw new BLException(errorCode, String.format(formatErrorMessage, className));
		return bean;
	}
}
