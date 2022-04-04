#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.rest;

import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.formbeans.ResponseFormBean;

/**
 * @author ${author}
 *
 */
public abstract class AbstractBaseResource {

	/***/
	private final Logger m_logger;

	/**
	 * @param clazz Class< ? extends AbstractBaseResource>
	 */
	protected AbstractBaseResource(final Class< ? extends AbstractBaseResource> clazz) {
		m_logger = LogManager.getLogger(clazz);
	}

	/**
	 * @return Logger
	 */
	protected Logger getLogger() {
		return m_logger;
	}

	/**
	 *
	 * @param collection ArrayList
	 * @param type Type
	 * @return Response
	 */
	@SuppressWarnings("rawtypes")
	protected String createListResponse(final List collection, final Type type) {
		final Gson gson = new Gson();
		final String data = gson.toJson(collection, type);
		return data;
	}

	/**
	 * @param image byte[]
	 * @return Reponse
	 */
	protected Response okResponse(final byte[] image) {
		return Response.status(GlobalStatusCodes.OK).entity(image).build();
	}

	/**
	 * @param status int
	 * @param data Serializable
	 * @return Reponse
	 */
	protected Response okResponse(final int status, final Object data) {
		final ResponseFormBean bean = new ResponseFormBean(status, null, data);
		final Gson gson = new Gson();
		return Response.status(GlobalStatusCodes.OK).entity(gson.toJson(bean)).build();
	}

	/**
	 * @param status int
	 * @return Reponse
	 */
	protected Response okResponse(final int status) {
		final ResponseFormBean bean = new ResponseFormBean(status, null, null);
		final Gson gson = new Gson();
		return Response.status(GlobalStatusCodes.OK).entity(gson.toJson(bean)).build();
	}

	/**
	 * @param ex AppException
	 * @return Response
	 */
	protected Response toResponse(final AppException ex) {
		m_logger.error(ex.getMessage(), ex);
		final ResponseFormBean bean = new ResponseFormBean(ex.getInternalStatus(), ex.getMessage(), null);
		final Gson gson = new Gson();
		return Response.status(ex.getGlobalStatus()).entity(gson.toJson(bean)).build();
	}

	/**
	 * @param ex Exception
	 * @return Response
	 */
	protected Response toResponse(final Exception ex) {
		m_logger.error(ex.getMessage(), ex);
		final ResponseFormBean bean = new ResponseFormBean(ServiceErrorStatus.SYSTEM_ERROR,
														   "Internal system error occured...",
														   null);
		final Gson gson = new Gson();
		return Response.status(GlobalStatusCodes.INTERNAL_SERVER_ERROR).entity(gson.toJson(bean)).build();
	}

}
