package com.ramosisw.jee.web.core.api.util;

import com.ramosisw.jee.web.core.api.ex.BLException;
import com.ramosisw.jee.web.core.api.BaseErrorCodes;

/**
 * 
 * @author jcramos
 *
 */
public class Bean {
	/**
	 * Obtiene la instancia de acceso a datos a partir de un EJB
	 * 
	 * @param <T>
	 *            tipo de la instancia <code>bean</code>
	 * @param bean
	 *            EJB del cual se quiere obtener la instancia
	 * @param className
	 *            nombre de la clase larga del EJB
	 * @return instancia EJB
	 * @throws BLException
	 *             en caso de no esta inicializado el EJB
	 */
	public static <T> T getBean(T bean, String className) throws BLException {
		return EJBUtils.getBean(bean, className, BaseErrorCodes.BL_EJB_NOT_INITIALIZED, "[%s] EJB was not initialized");
	}

	/**
	 * Obtiene la instancia de acceso a datos a partir de un EJB
	 * 
	 * @param <T>
	 *            tipo de la instancia <code>bean</code>
	 * @param bean
	 *            EJB del cual se quiere obtener la instancia
	 * @param type
	 *            tipo del objeto dao
	 * @return instancia EJB
	 * @throws BLException
	 *             en caso de no esta inicializado el EJB
	 */
	public static <T> T getBean(T bean, Class<T> type) throws BLException {
		return getBean(bean, type.getName());
	}
}
