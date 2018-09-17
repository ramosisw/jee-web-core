package com.ramosisw.jee.web.core.api.util;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Use ONLY for dev not for production
 * 
 * @author jcramos
 *
 */
public class KeyGenerator {
	public static String STRING_KEY = "j3e-W3b-C0r3";
	public static final Key DEFAULT_KEY = new SecretKeySpec(DatatypeConverter.parseBase64Binary(STRING_KEY),
			SignatureAlgorithm.HS512.getJcaName());
}
