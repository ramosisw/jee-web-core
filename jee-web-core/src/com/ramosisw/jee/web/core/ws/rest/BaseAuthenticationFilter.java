package com.ramosisw.jee.web.core.ws.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.ramosisw.jee.web.core.api.util.KeyGenerator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

/**
 * Usar esta clase como base en su AuthenticationFilter
 * @author jcramos
 *
 */
//@Priority(javax.ws.rs.Priorities.AUTHENTICATION)
public abstract class BaseAuthenticationFilter implements ContainerRequestFilter {

	private static final Logger log = Logger.getLogger(BaseAuthenticationFilter.class.getName());

	protected static String REALM = "HomeAssistant";
	protected static String AUTHENTICATION_SCHEME = "Bearer";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Get the Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Validate the Authorization header
		if (!isTokenBasedAuthentication(authorizationHeader)) {
			abortWithUnauthorized(requestContext);
			return;
		}

		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

		try {
			// Validate the token
			validateToken(token);
		} catch (Exception e) {
			log.error("#### invalid token : " + token);
			abortWithUnauthorized(requestContext);
		}
	}

	/**
	 * if token has uuid claims you can get it
	 * 
	 * @param headers del request
	 * @return the uuid
	 * @throws Exception
	 *             if the token no cotain uuid key in claims
	 */
	public static String getRequestUuid(HttpHeaders headers) throws Exception {
		// Get the Authorization header from the request
		String authorizationHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
		Jws<Claims> claims = Jwts.parser().setSigningKey(KeyGenerator.DEFAULT_KEY).parseClaimsJws(token);
		if (claims.getBody().containsKey("uuid"))
			return String.valueOf(claims.getBody().get("uuid"));
		throw new Exception("uuid was't found");
	}

	/**
	 * Check if the Authorization header is valid It must not be null and must be
	 * prefixed with "Bearer" plus a whitespace The authentication scheme comparison
	 * must be case-insensitive
	 * 
	 * @param authorizationHeader
	 * @return
	 */
	private boolean isTokenBasedAuthentication(String authorizationHeader) {
		return authorizationHeader != null
				&& authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}

	/**
	 * Abort the filter chain with a 401 status code response The WWW-Authenticate
	 * header is sent along with the response
	 * 
	 * @param requestContext
	 */
	private void abortWithUnauthorized(ContainerRequestContext requestContext) {
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
				.header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"").build());
	}

	/**
	 * 
	 * @param token
	 * @throws Exception
	 *             when token is invalid or inner validation
	 */
	protected abstract void validateToken(String token) throws Exception;

}
