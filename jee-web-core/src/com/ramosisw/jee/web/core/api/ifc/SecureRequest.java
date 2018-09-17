package com.ramosisw.jee.web.core.api.ifc;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Interfaz para solicitudes con seguridad
 * @author jcramos
 *
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface SecureRequest { }