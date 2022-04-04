#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.rest;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.formbeans.ResponseFormBean;
import ${package}.ui.util.WebUtil;

/**
 * @author ${author}
 *
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {

	/***/
	@Context
    private ResourceInfo m_resourceInfo;

	/***/
	@Context
    private HttpServletRequest m_request;

	/**
	 * @param requestContext
	 * @throws IOException
	 */
	@Override
	public void filter(final ContainerRequestContext requestContext) throws IOException {
		final Method method = m_resourceInfo.getResourceMethod();

		if (!method.isAnnotationPresent(PermitAll.class)) {
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(accessForbidden());
	            return;
			}

			if (!WebUtil.isAuthenticatedWeb(m_request)) {
				requestContext.abortWith(accessUnauthorized());
			}
		}

	}

	/**
	 * @return Response
	 */
	private Response accessUnauthorized() {
		return toResponse(GlobalStatusCodes.FORBIDDEN,
						  ServiceErrorStatus.SYSTEM_ERROR,
						  "You cannot access this resource");
	}

	/**
	 * @return Response
	 */
	private Response accessForbidden() {
		return toResponse(GlobalStatusCodes.FORBIDDEN,
						  ServiceErrorStatus.SYSTEM_ERROR,
						  "You cannot access this resource");
	}



	/**
	 * @param globalStatus int
	 * @param internalStatus int
	 * @param message String
	 * @return Response
	 */
	protected Response toResponse(final int globalStatus, final int internalStatus, final String message) {
		final ResponseFormBean bean = new ResponseFormBean(internalStatus, message, null);
		final Gson gson = new Gson();
		return Response.status(globalStatus).entity(gson.toJson(bean)).build();
	}

}
