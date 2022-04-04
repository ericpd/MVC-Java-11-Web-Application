#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ${package}.text.Paths;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.XSSRequestWrapper;

/**
 * @author ${author}
 */
@WebFilter(filterName = "PageFilter",
urlPatterns = {"/*", "*.jsp"},
dispatcherTypes = {
	DispatcherType.REQUEST
})
public final class PageFilter implements Filter {

	/***/
	private static final Logger LOGGER = LogManager.getLogger(PageFilter.class);

	/***/
	public PageFilter() {
	}

	@Override
	public void init(final FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(final ServletRequest request,
						 final ServletResponse response,
						 final FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		request.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession(false);
		if (null == session) {
			session = req.getSession(true);
		}

		final String requestURI = req.getRequestURI();
		String contextPath = "";
		if (null != req.getContextPath()) {
			contextPath = req.getContextPath();
		}
		final String requestPath = requestURI.substring(contextPath.length(), requestURI.length());

		if (requestPath.endsWith(".jsp")) {
			LOGGER.warn("Invalid page access: " + requestURI);
			session.setAttribute(WebConst.ATTR_STATUS, 404);
			session.setAttribute(WebConst.ATTR_MESSAGE, "Not found...");
			res.sendRedirect(contextPath + Paths.getPath("Error.Servlet"));
			return;
		}

		// check exact path to allow access
		if (Paths.getAllExactPaths().contains(requestPath)) {
			if	(!(requestPath.endsWith(".js") ||
					requestPath.endsWith(".css") ||
					requestPath.endsWith(".map"))) {
				LOGGER.debug("Request called: " + requestPath);
			}
			chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
			return;
		}

		// check starts with path to allow access
		for (final String path : Paths.getAllStartsWithPaths()) {
			if (requestPath.startsWith(path)) {
				if	(!(requestPath.endsWith(".js") ||
						requestPath.endsWith(".css") ||
						requestPath.endsWith(".png") ||
						requestPath.endsWith(".map"))) {
					LOGGER.debug("Request called: " + requestPath);
				}
				chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
				return;
			}
		}

		LOGGER.warn("Invalid page access: " + requestURI);
		session.setAttribute(WebConst.ATTR_STATUS, 404);
		session.setAttribute(WebConst.ATTR_MESSAGE, "Not found...");
		res.sendRedirect(contextPath + Paths.getPath("Error.Servlet"));
		return;
	}

	@Override
	public void destroy() {
	}
}
