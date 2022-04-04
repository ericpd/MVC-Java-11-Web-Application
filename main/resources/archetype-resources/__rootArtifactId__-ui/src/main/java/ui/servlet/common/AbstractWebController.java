#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.formbeans.ResponseFormBean;
import ${package}.ui.constants.WebConst;
import ${package}.util.AppUtil;
import ${package}.util.SystemSettingsUtil;

/**
 * @author ${author}
 *
 */
public abstract class AbstractWebController extends AbstractBaseSvt {

	private static final long serialVersionUID = 1L;

	/**
	 * @param clazz Class< ? extends AbstractWebController>
	 */
	protected AbstractWebController(final Class< ? extends AbstractWebController> clazz) {
        super(clazz);
	}

	@Override
	protected final void doWork(final HttpServletRequest request,
								final HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(false);
		final String method = request.getMethod();
		if (hasMethod(method)) {
			final DataModel model = new DataModel(request.getContentType(),
												  createRequestModel(request),
												  createSessionModel(session));
			try {
				process(method, model);

				// save request page if its not an ajax call or print request
				if ("GET".equalsIgnoreCase(request.getMethod()) && !model.isPrintOutput()) {
					model.getSession().put(WebConst.ATTR_PREV_PAGE, getRedirectPath(request));
				}

			} catch (final AppException e) {
				getLogger().error(e);
				if (model.isPrintOutput()) {
					model.clearOut(); //clear all set output beans
					response.setStatus(e.getGlobalStatus());
					model.out(new ResponseFormBean(e.getGlobalStatus(), e.getMessage(), null));
				} else if (AppUtil.isNullOrEmpty(model.getRedirectPath())) {
					redirectErrorPage(request, response, e.getGlobalStatus(), e.getMessage());
					return;
				}
			}

			processResponse(request, response, model);
		} else {
			redirectErrorPage(request, response, 404, "Not found...");
		}
	}

	/**
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model DataModel
	 * @throws ServletException e
	 * @throws IOException e
	 */
	private void processResponse(final HttpServletRequest request,
								 final HttpServletResponse response,
								 final DataModel model) throws ServletException, IOException {
		// Set request and session even for printed output...
		setRequest(request,
					model.getRequest().get());
		setSession(request,
					model.getSession().get(),
					model.getSession().remAttrs(),
					model.getSession().isNew(),
					model.getSession().isInvalidate());

		if (model.isPrintOutput()) {
			response.setContentType(model.getContentType());
			if ("application/pdf".equals(model.getContentType())) {
				final OutputStream os = response.getOutputStream();
				final PdfDocument doc = new PdfDocument(new PdfWriter(os));
				if (model.isPortrate()) {
					doc.setDefaultPageSize(PageSize.LETTER);
				} else {
					doc.setDefaultPageSize(PageSize.LETTER.rotate());
				}
				HtmlConverter.convertToPdf(model.getHtml(), doc, new ConverterProperties());
				doc.close();
				os.flush();
			} else {
				final PrintWriter out = response.getWriter();
				if (model.getOut().size() == 1) {
					out.write(new JSONObject(model.getOut().get(0)).toString());
				} else {
					for (final Object obj : model.getOut()) {
						final String str = (String) obj;
						if (WebConst.NEW_LINE.equals(str)) {
							out.println();
						} else {
							out.print(str);
						}
					}
				}
				out.flush();
			}
		} else {
			if (!AppUtil.isNullOrEmpty(model.getRedirectPath())) {
				if (model.isRedirect()) {
					String contextPath = "";
					if (null != request.getContextPath()) {
						contextPath = request.getContextPath();
					}
					response.sendRedirect(contextPath + model.getRedirectPath());
				} else {
					request.getRequestDispatcher(model.getRedirectPath()).forward(request, response);
				}
			}
		}
	}

	/**
	 * @param request HttpServletRequest
	 * @param properties Properties
	 * @param removeAttrs list of String
	 * @param isNew boolean
	 * @param invalidate boolean
	 */
	private void setSession(final HttpServletRequest request,
							final Properties properties,
							final List<String> removeAttrs,
							final boolean isNew,
							final boolean invalidate) {
		HttpSession session = request.getSession(isNew);
		if (null != session && invalidate) {
			session.invalidate();
			return;
		}

		if (null == session) {
			// create new session if there's none
			session = request.getSession(true);
			session.setMaxInactiveInterval(SystemSettingsUtil.getSettingAsInt("system.session.timeout", 2) * 60);
		}

		if (properties.size() > 0) {
			final Enumeration< ? > keys = properties.propertyNames();
			while (keys.hasMoreElements()) {
				final String key = (String) keys.nextElement();
				session.setAttribute(key, properties.get(key));
			}

			for (final String attr : removeAttrs) {
				session.removeAttribute(attr);
			}
		}
	}

	/**
	 * @param request HttpServletRequest
	 * @param properties Properties
	 */
	private void setRequest(final HttpServletRequest request,
							final Properties properties) {
		//clear request attributes
		final Enumeration< ? > attrKeys = request.getAttributeNames();
		while (attrKeys.hasMoreElements()) {
			final String key = (String) attrKeys.nextElement();
			request.removeAttribute(key);
		}

		final Enumeration< ? > keys = properties.propertyNames();
		while (keys.hasMoreElements()) {
			final String key = (String) keys.nextElement();
			request.setAttribute(key, properties.get(key));
		}

		String requestURL = request.getRequestURL().toString();
		final String requestURI = request.getRequestURI();
		requestURL = requestURL.substring(0, requestURL.length() - requestURI.length());
		if (SystemSettingsUtil.getSettingAsBool("system.https.enable", false)) {
			requestURL = requestURL.replace("http://", "https://");
		}
		request.setAttribute("modifiedRequestURL", requestURL);
	}

	/**
	 * @param method String
	 * @param model DataModel
	 * @throws AppException e
	 */
	private void process(final String method, final DataModel model) throws AppException {
		switch (method) {
		case "GET":
			processGet(model);
			break;
		case "POST":
			processPost(model);
			break;
		case "DELETE":
			processDelete(model);
			break;
		case "PUT":
			processPut(model);
			break;

		default:
			break;
		}
	}

	/**
	 * @param request HttpServletRequest
	 * @return Request
	 */
	private Request createRequestModel(final HttpServletRequest request) {
		final Properties param = new Properties();
		final Enumeration<String> paramKeys = request.getParameterNames();
		while (paramKeys.hasMoreElements()) {
			final String key = paramKeys.nextElement();
			final String val = request.getParameter(key);
			param.put(key, val);
		}

		final Enumeration<String> attrKeys = request.getAttributeNames();
		final Properties attr = new Properties();
		while (attrKeys.hasMoreElements()) {
			final String key = attrKeys.nextElement();
			final Object val = request.getAttribute(key);
			attr.put(key, val);
		}

		return new Request(attr,
						   param,
						   request.getScheme(),
						   request.getServerName(),
						   request.getServerPort(),
						   request.getContextPath());
	}

	/**
	 * @param session HttpSession
	 * @return Session
	 */
	private Session createSessionModel(final HttpSession session) {
		final Properties props = new Properties();
		boolean isLoggedIn = false;
		String sessionId = null;
		if (null != session) {
			final Enumeration<String> keys = session.getAttributeNames();
			while (keys.hasMoreElements()) {
				final String key = keys.nextElement();
				final Object val = session.getAttribute(key);
				props.put(key, val);
			}

			final String userId = (String) session.getAttribute(WebConst.ATTR_USER_USERID);
			isLoggedIn = null != userId;
			sessionId = session.getId();
		}
		return new Session(sessionId, props, isLoggedIn);
	}

	/**
	 * @param method String
	 * @return boolean
	 */
	private boolean hasMethod(final String method) {
		if (Arrays.asList(supportMethod()).contains(method)) {
			return true;
		}
		return false;
	}

	/**
	 * Override this method if you want to add other Methods.
	 * Possible Methods: GET, PUT, DELETE, POST
	 * @return String[]
	 */
	protected String[] supportMethod() {
		return new String[] {"GET", "POST"};
	}

	/**
	 * @param model DataModel
	 * @throws AppException e
	 */
	protected void processGet(final DataModel model) throws AppException {
		throw new AppException(GlobalStatusCodes.NOT_FOUND_MESSAGE, GlobalStatusCodes.NOT_FOUND);
	}

	/**
	 * @param model DataModel
	 * @throws AppException e
	 */
	protected void processPost(final DataModel model) throws AppException {
		throw new AppException(GlobalStatusCodes.NOT_FOUND_MESSAGE, GlobalStatusCodes.NOT_FOUND);
	}

	/**
	 * Override this method to handle PUT Method.
	 * @param model DataModel
	 * @throws AppException e
	 */
	protected void processPut(final DataModel model) throws AppException {
		throw new AppException(GlobalStatusCodes.NOT_FOUND_MESSAGE, GlobalStatusCodes.NOT_FOUND);
	}

	/**
	 * Override this method to handle DELETE Method.
	 * @param model DataModel
	 * @throws AppException e
	 */
	protected void processDelete(final DataModel model) throws AppException {
		throw new AppException(GlobalStatusCodes.NOT_FOUND_MESSAGE, GlobalStatusCodes.NOT_FOUND);
	}

	/**
	 * @param method String
	 * @return boolean
	 */
	@Override
	protected boolean byPassLogin(final String method) {
		return false;
	}

	/**
	 *
	 * @param model DataModel
	 * @param path String
	 * @param portrate boolean
	 * @throws AppException
	 */
	protected void renderJspAsPDF(final DataModel model, final String path, final boolean portrate) throws AppException {
		try {
			model.setOutputAsPDF();
			model.setPortrate(portrate);
			final URLConnection connection = new URL(path).openConnection();
			connection.setRequestProperty("Cookie", "JSESSIONID=" + model.getSession().getSessionID());
			final InputStream source = connection.getInputStream();
			final StringWriter writer = new StringWriter();
			IOUtils.copy(source, writer, "UTF-8");
			model.setHtml(writer.toString());
			source.close();
			writer.close();
		} catch (final MalformedURLException e) {
			getLogger().error(e);
			throw new AppException("Internal server error...", GlobalStatusCodes.INTERNAL_SERVER_ERROR, ServiceErrorStatus.INTERNAL_ERROR);
		} catch (final IOException e) {
			getLogger().error(e);
			throw new AppException("Internal server error...", GlobalStatusCodes.INTERNAL_SERVER_ERROR, ServiceErrorStatus.INTERNAL_ERROR);
		}
	}
}
