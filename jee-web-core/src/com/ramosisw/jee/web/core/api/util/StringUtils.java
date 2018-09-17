package com.ramosisw.jee.web.core.api.util;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Metodos de ayuda para el manejo de strings
 * 
 * @author jcramos
 */
public class StringUtils {

	/**
	 * Quita los espacios al inicio y al final de la cadena
	 * 
	 * @param data
	 *            String Cadena con espacios
	 * @return La cadena sin espacio
	 */
	public static String trimString(String data) {
		if (data == null)
			return data;
		else
			return data.trim();
	}

	/**
	 * Verifica si el string data es null o su longitud es 0
	 * 
	 * @param data
	 *            String a validar
	 * @return Verdadero si el string es = null o está vacio
	 */
	public static boolean isEmpty(String data) {
		return (data == null || data.trim().length() == 0);
	}

	/**
	 * Obtiene un nuevo UUID
	 * 
	 * @return String con el uuid generado
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Genera un UUID y le aplica el algortimo de hashing MD5
	 * 
	 * @return String de 32 caracteres
	 */
	public static String getUUIDAsMD5() {
		return DigestUtils.md5Hex(getUUID());
	}

	/**
	 * Aplica el algortimo de hashing MD5 al string con el valor especificado
	 * 
	 * @param value
	 *            String a ser hasheado a md5
	 * @return String de 32 caracteres
	 */
	public static String md5(String value) {
		return DigestUtils.md5Hex(value);
	}

	/**
	 * Valida si un string es un hash tipo SHA1
	 * 
	 * @param s
	 *            String a verificar
	 * @return Verdadero si el strign es un hash SHA1
	 */
	public static boolean isValidSHA1(String s) {
		try {
			return s.matches("[a-fA-F0-9]{40}");
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Valida si un string es un hash tipo MD5
	 * 
	 * @param s
	 *            String a verificar
	 * @return Verdadero si el strign es un hash MD5
	 */
	public static boolean isValidMD5(String s) {
		try {
			return s.matches("[a-fA-F0-9]{32}");
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Finds the value of the given enumeration by name, case-insensitive. Throws an
	 * IllegalArgumentException if no match is found.
	 * 
	 * @param <T> Tipo del Enumerador
	 * @param enumeration
	 *            enumeration
	 * @param name
	 *            nombre
	 * 
	 * @return Enum T valor encontrado segun el nombre y el enumerador
	 * @see <a href=
	 *      "http://stackoverflow.com/questions/604424/java-convert-string-to-enum"
	 *      target=
	 *      "blank">http://stackoverflow.com/questions/604424/java-convert-string-to-enum</a>
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Enum<T>> T valueOfIgnoreCase(Class<T> enumeration, String name) {
		if (name == null)
			throw new IllegalArgumentException("String enum name can't be a null reference!");
		name = name.replace('-', '_'); // Las enumeraciones en Java no permiten el "-"
		for (Enum enumValue : enumeration.getEnumConstants()) {
			if (enumValue.name().equalsIgnoreCase(name)) {
				return (T) enumValue;
			}
		}
		throw new IllegalArgumentException(
				"There is no value with name '" + name + " in Enum " + enumeration.getClass().getName());
	}

	/**
	 * Decodifica el string en base64 usando la librería de apache commons.
	 * 
	 * @param base64String
	 *            String en base64
	 * @return Arreglo de bytes decodificado
	 */
	public static byte[] decodeBase64(String base64String) {
		if (base64String == null)
			throw new IllegalArgumentException("base64String can't be null reference!");

		return Base64.decodeBase64(base64String);
	}

	/**
	 * Codifica el arreglo de bytes a un string en base64 usando la librer�a de
	 * apache commons.
	 * 
	 * @param data
	 *            Bytes a codificar
	 * @return String codificado en Base64
	 */
	public static String encodeBase64(byte[] data) {
		if (data == null)
			throw new IllegalArgumentException("data byte array can't be null reference!");

		return Base64.encodeBase64String(data);
	}

	/**
	 * Obtener la cadena de izquierda a derecha con un maximo de caracteres
	 * permitidos.
	 * 
	 * @param str
	 *            Cadena a obtener con el maximo de caracteres.
	 * @param maxChars
	 *            maximo numero de elementos que contendra la cadena.
	 * @return cadena recortada con el maximo numero de caractes o inferior de la
	 *         cadena original.
	 */
	public static String substring(String str, int maxChars) {
		return str.substring(0, str.length() >= maxChars ? maxChars : str.length());
	}

	/**
	 * Obtener la cadena de derecha a izquierda con un maximo de caracteres
	 * permitidos.
	 * 
	 * @param str
	 *            Cadena a obtener con el maximo de caracteres.
	 * @param maxChars
	 *            maximo numero de elementos que contendra la cadena.
	 * @return cadena recortada con el maximo numero de caracteres o inferior de la
	 *         cadena original.
	 */
	public static String substringInverse(String str, int maxChars) {
		return str.substring(str.length() == maxChars ? 0 : Math.max(0, str.length() - maxChars));
	}

	/**
	 * Obtener la cadena de izquierda a derecha con un pad right
	 * 
	 * @param str
	 *            cadena original
	 * @param maxChars
	 *            maximo de caracteres permitidos de la cadena original
	 * @param pad
	 *            caracter a remplzar los espacios cuando la cadena es inferior al
	 *            #maxChars
	 * @return cadena transformada
	 */
	public static String substring(String str, int maxChars, char pad) {
		return String.format("%1$-" + maxChars + "s", substring(str, maxChars)).replace(' ', pad);
	}

	/**
	 * Obtener la cadena de derecha a izquierda con un pad left
	 * 
	 * @param str
	 *            cadena original
	 * @param maxChars
	 *            maximo de caracteres permitidos de la cadena original
	 * @param pad
	 *            caracter a remplzar los espacios cuando la cadena es inferior al
	 *            #maxChars
	 * @return cadena transformada
	 */
	public static String substringInverse(String str, int maxChars, char pad) {
		return String.format("%1$" + maxChars + "s", substringInverse(str, maxChars)).replace(' ', pad);
	}

	/**
	 * @param str
	 *            cadena a la cual se agregara el pad
	 * @param pad
	 *            numero de pads
	 * @param padChar
	 *            caracter a ser utilizado en el pad
	 * @return cadena con el nuevo pad
	 */
	public static String padLeft(String str, int pad, char padChar) {
		return String.format("%1$" + pad + "s", substring(str, pad)).replace(' ', padChar);
	}

	/**
	 * @param str
	 *            cadena a la cual se agregara el pad
	 * @param pad
	 *            numero de pads
	 * @param padChar
	 *            padChar caracter a ser utilizado en el pad
	 * @return cadena con el nuevo pad
	 */
	public static String padRight(String str, int pad, char padChar) {
		return String.format("%1$-" + pad + "s", substring(str, pad)).replace(' ', padChar);
	}
}
