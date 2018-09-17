package com.ramosisw.jee.web.core.api.to;

/**
 * Clase para el manejo de autenticacion basica con usuario y contraseña
 * @author jcramos
 *
 */
public class CredentialType {
	protected String userName;
	protected String password;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CredentialType [userName=" + userName + ", password=" + password + "]";
	}

}
