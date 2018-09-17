package com.ramosisw.jee.web.core.ws;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.jboss.logging.Logger;

/**
 * Clase base para el binding de un servicio web
 * @author jcramos
 *
 */
public class ServiceBinding {

	private static final Logger LOG = Logger.getLogger(ServiceBinding.class.getName());

	@Resource
	private WebServiceContext wsContext;

	/**
	 * @return Referencia al contexto WebServiceContext de la petición en
	 *         curso.
	 */
	public WebServiceContext getWsContext() {
		return wsContext;
	}

	/**
	 * @return Instancia al logger ya inicializado
	 */
	public Logger getLog() {
		return LOG;
	}

	/**
	 * 
	 * @return String que contiene la IP y el puerto del cliente que hace la
	 *         invocación de la operación del WS.
	 * 
	 *         El formato del string es IP:Port
	 * 
	 * @see <a href="http://cxf.547215.n5.nabble.com/Getting-the-IP-Address-of-SOAP-Clients-works-for-REST-clients-but-not-for-SOAP-clients-td4476334.html" target="blank">http://cxf.547215.n5.nabble.com/Getting-the-IP-Address-of-SOAP-Clients-works-for-REST-clients-but-not-for-SOAP-clients-td4476334.html</a>
	 */
	public String getRemoteHost() {
		StringBuilder add = new StringBuilder();
		HttpServletRequest request = null;

		try {
			request = (HttpServletRequest) getWsContext().getMessageContext().get(MessageContext.SERVLET_REQUEST);
		} catch (Exception ex) {
			getLog().error("[getRemoteHost] Exception:", ex);
		}

		if (request != null) {
			add.append(request.getRemoteAddr());
			if (request.getRemotePort() > 0)
				add.append(":" + request.getRemotePort());
		} else
			add.append("UNKNOWN_HOST");

		return add.toString();
	}

}
