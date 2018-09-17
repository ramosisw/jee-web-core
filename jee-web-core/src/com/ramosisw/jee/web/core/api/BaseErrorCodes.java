package com.ramosisw.jee.web.core.api;

/**
 * Codigos de error base para el control de excepciones
 * 
 * @author jcramos
 *
 */
public class BaseErrorCodes {
	public static final int DL_BASE_CODE = 3000;
	public static final int BL_BASE_CODE = 2000;
	public static final int WS_BASE_CODE = 1000;

	public static final int BL_EJB_NOT_INITIALIZED = 666;

	public static final int BL_FILE_NOT_FOUND = BL_BASE_CODE + 404;
	public static final int BL_IOEXCEPTION = BL_BASE_CODE + 500;
	public static final int DL_PERSISTENCE = DL_BASE_CODE + 500;
}
