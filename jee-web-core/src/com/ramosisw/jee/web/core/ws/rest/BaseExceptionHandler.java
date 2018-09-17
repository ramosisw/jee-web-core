package com.ramosisw.jee.web.core.ws.rest;

import javax.naming.ServiceUnavailableException;
import javax.resource.NotSupportedException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import com.ramosisw.jee.web.core.api.ex.BLException;
import com.ramosisw.jee.web.core.ws.rest.messages.ErrorMessage;

/**
 * Controlara las excepciones derivadas de Exception.
 * 
 * Cuando se defina una aplicacion RESTful esta clase sera implementada
 * directamente para el control de excepciones, se recomienda que los metodos
 * tengan como tipo de error Exception ejemplo
 * 
 * <pre>
 * &#64;Path("/")
 * &#64;Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
 * &#64;Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
 * public interface RestService {
 * 
 * 	&#64;GET
 * 	&#64;Path("hello_world")
 * 	public BasicMessage helloWorld() throws Exception;
 * }
 * </pre>
 * 
 * y en la implementacion poder lanzar excepciones controladas de tipo BLException
 * o Exception como se muestra a continuacion
 * 
 * <pre>
 * public class RestServiceImpl implements RestService {
 * 
 * 	&#64;Override
 * 	public BasicMessage helloWorld() throws Exception {
 * 		throw new BLException("Metodo no implementado");
 * 	}
 * }
 * </pre>
 * 
 * @author jcramos
 *
 */
@Provider
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML })
public class BaseExceptionHandler implements ExceptionMapper<Exception> {

	/**
	 * Defualt constructor
	 */
	public BaseExceptionHandler() {
		super();
	}

	private static final Logger log = Logger.getLogger(BaseExceptionHandler.class.getName());

	@Override
	public Response toResponse(Exception ex) {
		log.error(ex);

		Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
		ErrorMessage error = new ErrorMessage(httpStatus.getStatusCode(), ex.getMessage());
		error.setCode(666);
		if (error.getMessage() == null || (error.getMessage() != null && error.getMessage().isEmpty()))
			error.setMessage("Not ExceptionMessage found");

		if (ex instanceof BLException) {
			httpStatus = Response.Status.BAD_REQUEST;
			BLException blEx = (BLException) ex;
			// obtener el codigo del error cuando el error es diferente de nulo
			if (blEx.getError() != null)
				error.setCode(blEx.getError().getCode());
		} else if (ex instanceof NullPointerException) {
			httpStatus = Response.Status.BAD_REQUEST;
			error.setMessage("Empty request");
		} else if (ex instanceof BadRequestException)
			httpStatus = Response.Status.BAD_REQUEST;
		else if (ex instanceof NotAuthorizedException)
			httpStatus = Response.Status.UNAUTHORIZED;
		else if (ex instanceof ForbiddenException)
			httpStatus = Response.Status.FORBIDDEN;
		else if (ex instanceof NotFoundException)
			return Response.status(Response.Status.NOT_FOUND).build();
		else if (ex instanceof NotAllowedException)
			return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
		else if (ex instanceof NotAcceptableException)
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		else if (ex instanceof NotSupportedException)
			return Response.status(Response.Status.HTTP_VERSION_NOT_SUPPORTED).build();
		else if (ex instanceof InternalServerErrorException)
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		else if (ex instanceof ServiceUnavailableException)
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
		else
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

		try {
			log.info(error);
			return Response.status(httpStatus).entity(error).build();
		} catch (Exception excep) {
			log.error(excep);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
