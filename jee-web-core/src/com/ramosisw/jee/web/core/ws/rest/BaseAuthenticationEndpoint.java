package com.ramosisw.jee.web.core.ws.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 
 * @author jcramos
 *
 */
public abstract class BaseAuthenticationEndpoint<AuthType> {

	@Context
	protected UriInfo uriInfo;

	/**
	 * For POST method, auth (login) user to system.
	 * 
	 * @param authType
	 *            Object with data can sends user
	 * @return request of authentication
	 */
	public abstract Response authenticate(AuthType authType);

	/**
	 * Method for authentication, it's login are not valid throws an exception like
	 * javax.ws.rs.NotAllowedException
	 * 
	 * @param authType
	 *            data to valid auth
	 * @throws Exception
	 *             when data is not valid
	 */
	public abstract void validAuthentication(AuthType authType) throws Exception;

	/**
	 * for DELETE method, close or revoke token
	 * 
	 * @return a response of request
	 */
	public abstract Response authenticate();

	/**
	 * Issue a token (can be a random String persisted to a database or a JWT token)
	 * For example String jwtToken =
	 * Jwts.builder().setId(username).setSubject(username)
	 * .setIssuer(uriInfo.getAbsolutePath().toString()).setIssuedAt(new Date())
	 * .setExpiration(toDate(LocalDateTime.now().plusMinutes(expirationMinutes)))
	 * .signWith(SignatureAlgorithm.HS512, KeyGenerator.DEFAULT_KEY).claim("uuid",
	 * UUID.randomUUID()) .compact();
	 * 
	 * @param authType
	 *            The issued token must be associated to a user
	 * @return jwt token
	 */
	public abstract String issueToken(AuthType authType);

}
