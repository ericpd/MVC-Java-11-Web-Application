#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.common;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ${package}.constants.GlobalStatusCodes;
import ${package}.text.Paths;
import ${package}.ui.constants.WebConst;
import ${package}.ui.util.WebUtil;

/**
 * @author ${author}
 *
 */
public abstract class AbstractBaseSvt extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	/***/
	private final Logger m_logger;

	/***/
	private ServletSettings m_settings;

	/**
	 * @param clazz Class< ? extends AbstractBaseSvt>
	 */
	protected AbstractBaseSvt(final Class< ? extends AbstractBaseSvt> clazz) {
		super();
		m_logger = LogManager.getLogger(clazz);
	}

	@Override
	public final void init(final ServletConfig config) throws ServletException {
		super.init(config);
		m_settings = (ServletSettings) config.getServletContext().getAttribute("settings");
	}

	/**
	 * @return ServletSettings
	 */
	protected ServletSettings getSettings() {
		return m_settings;
	}

	@Override
	protected final void doDelete(final HttpServletRequest request,
						 	final HttpServletResponse response) throws ServletException, IOException {
		superDoWork(request, response);
	}

	@Override
	protected final void doGet(final HttpServletRequest request,
						 	   final HttpServletResponse response) throws ServletException, IOException {
		superDoWork(request, response);
	}

	@Override
	protected final void doPut(final HttpServletRequest request,
						 	   final HttpServletResponse response) throws ServletException, IOException {
		superDoWork(request, response);
	}

	@Override
	protected final void doPost(final HttpServletRequest request,
								final HttpServletResponse response) throws ServletException, IOException {
		superDoWork(request, response);
	}

	/**
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException e
	 * @throws IOException e
	 */
	private void superDoWork(final HttpServletRequest request,
							 final HttpServletResponse response) throws ServletException, IOException {
		// check if the user has been authenticated
		if (byPassLogin(request.getMethod())) {
			response.setCharacterEncoding(m_settings.getOutputEncoding());
			doWork(request, response);
			return;
		}

		if (WebUtil.isAuthenticatedWeb(request)) {
			if (isUserAllowedAccess(request)) {
				response.setCharacterEncoding(m_settings.getOutputEncoding());
				doWork(request, response);
			} else {
				// just show not found instead of access not allowed to tell prevent anyone from accessing random links
				redirectErrorPage(request, response, GlobalStatusCodes.NOT_FOUND, GlobalStatusCodes.NOT_FOUND_MESSAGE);
			}

		} else {
			final String loginPath = Paths.getPath("Login.Servlet");
			response.sendRedirect(request.getContextPath() + loginPath + "?" + WebConst.ATTR_REDIRECT_URL + "=" + getRedirectPath(request));
		}
	}

	/**
	 *
	 * @param request HttpServletRequest
	 * @return the redirect path
	 */
	protected String getRedirectPath(final HttpServletRequest request) {
		String contextPath = "";
		if (null != request.getContextPath()) {
			contextPath = request.getContextPath();
		}
		final String requestURI = request.getRequestURI();
		final String requestPath = requestURI.substring(contextPath.length(), requestURI.length());

		if ("/competency".equals(requestPath)) {
			return requestPath; // remove all paremeter in conpetency page
		}
		return appendAllParameters(request, requestPath);
	}

	/**
	 *
	 * @param request HttpServletRequest
	 * @param requestPath String
	 * @return String
	 */
	private String appendAllParameters(final HttpServletRequest request, final String requestPath) {
		final StringBuffer sb = new StringBuffer(requestPath);
		final Enumeration<String> paramKeys = request.getParameterNames();
		int index = 0;
		while (paramKeys.hasMoreElements()) {
			if (0 == index) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			final String key = paramKeys.nextElement();
			final String val = request.getParameter(key);
			sb.append(key + "=" + val);
			index++;
		}
		return sb.toString();
	}

	/**
	 *
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	private boolean isUserAllowedAccess(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (null != session) {
			final String userType = (String) session.getAttribute(WebConst.ATTR_USER_TYPE);
			return allowedUserType(request.getMethod(), userType);
		}
		return false;
	}

	/**
	 *
	 * @param method String
	 * @param userType String
	 * @return boolean
	 */
	protected boolean allowedUserType(final String method, final String userType) {
		return true;
	}

	/**
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param statusCode int
	 * @param message String
	 * @throws IOException e
	 */
	protected void redirectErrorPage(final HttpServletRequest request,
									 final HttpServletResponse response,
									 final int statusCode,
									 final String message) throws IOException {
		HttpSession session = request.getSession(false);
		if (null == session) {
			session = request.getSession(true);
		}
		session.setAttribute(WebConst.ATTR_STATUS, statusCode);
		session.setAttribute(WebConst.ATTR_MESSAGE, message);
		response.sendRedirect(request.getContextPath() + Paths.getPath("Error.Servlet"));
	}

	/**
	 * @param method String
	 * @return boolean
	 */
	protected abstract boolean byPassLogin(String method);

	/**
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException e
	 * @throws IOException e
	 */
	protected abstract void doWork(HttpServletRequest request,
								   HttpServletResponse response) throws ServletException, IOException;

	/**
	 * @return Logger
	 */
	protected Logger getLogger() {
		return m_logger;
	}

}
