#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.common;

import java.util.ArrayList;
import java.util.List;

import ${package}.formbeans.ResponseFormBean;
import ${package}.text.Paths;
import ${package}.ui.constants.WebConst;
import ${package}.util.AppUtil;

/**
 * @author ${author}
 *
 */
public class DataModel {

	/***/
	private final String m_userId;

	/***/
	private final String m_userType;

	/***/
	private final Request m_request;

	/***/
	private final Session m_session;

	/***/
	private final List<Object> m_out;

	/***/
	private String m_contentType;

	/***/
	private boolean m_redirect;

	/***/
	private String m_redirectPath;

	/***/
	private boolean m_printOutput = false;

	/** Only used for PDF output. **/
	private String m_html;

	/***/
	private boolean m_portrate = true;

	/**
	 * @param contentType
	 * @param request Request
	 * @param session Session
	 */
	public DataModel(final String contentType,
					 final Request request,
					 final Session session) {
		this.m_request = request;
		this.m_session = session;
		if (null != m_session) {
			m_userId = m_session.getAttributeAsString(WebConst.ATTR_USER_USERID, null);
			m_userType = m_session.getAttributeAsString(WebConst.ATTR_USER_TYPE, null);
		} else {
			m_userId = null;
			m_userType = null;
		}
		this.m_out = new ArrayList<>();
		this.m_contentType = contentType;
		this.m_redirectPath = null;
		this.m_redirect = false;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return m_userId;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return m_userType;
	}

	/**
	 * @return the redirectPath
	 */
	public String getRedirectPath() {
		return m_redirectPath;
	}

	/**
	 *
	 * @param targetPath targetPath
	 * @param redirect boolean
	 */
	public void setRedirectPath(final String targetPath, final boolean redirect) {
		this.m_redirect = redirect;
		this.m_redirectPath = targetPath;
	}

	/**
	 * @param targetJsp the redirectPath to set
	 */
	public void setRedirectAjaxJsp(final String targetJsp) {
		setRedirectJsp(WebConst.JSP_AJAX_PAGE, targetJsp, null, null, null);
	}

	/**
	 * @param targetJsp the redirectPath to set
	 */
	public void setRedirectContentJsp(final String targetJsp) {
		setRedirectJsp(WebConst.JSP_CONTENT_PAGE, targetJsp, null, null, null);
	}

	/**
	 * @param targetJsp the redirectPath to set
	 */
	public void setRedirectJsp(final String targetJsp) {
		setRedirectJsp(WebConst.JSP_COMMON_PAGE, targetJsp, null, null, null);
	}

	/**
	 * @param targetJsp the redirectPath to set
	 */
	public void setRedirectCommonJsp(final String targetJsp) {
		setRedirectJsp(WebConst.JSP_COMMON_PAGE, targetJsp, "commonheader", null, "commonsidebar");
	}

	/**
	 * @param redirectPath the redirectPath to set
	 * @param targetJsp the targetJsp to set
	 * @param headerJsp to add header to the page
	 * @param footerJsp to add footer to the page
	 */
	public void setRedirectJsp(final String redirectPath,
			   				   final String targetJsp,
							   final String headerJsp,
							   final String footerJsp) {
		setRedirectJsp(redirectPath, targetJsp, headerJsp, footerJsp, null);
	}

	/**
	 * @param redirectPath the redirectPath to set
	 * @param targetJsp the targetJsp to set
	 * @param headerJsp to add header to the page
	 * @param footerJsp to add footer to the page
	 * @param sidebarJsp to add side bar to the page
	 */
	public void setRedirectJsp(final String redirectPath,
			   				   final String targetJsp,
							   final String headerJsp,
							   final String footerJsp,
							   final String sidebarJsp) {
		this.m_redirect = false; //always false for JSP pages
		this.m_redirectPath = redirectPath;
		this.m_request.put("targetJsp", targetJsp);
		if (null != headerJsp) {
			this.m_request.put("headerJsp", headerJsp);
		}
		if (null != footerJsp) {
			this.m_request.put("footerJsp", footerJsp);
		}
		if (null != sidebarJsp) {
			this.m_request.put("sidebarJsp", sidebarJsp);
		}
	}

	/**
	 * @param jspFile the jspFile to set
	 */
	public void setRedirectJspOnly(final String jspFile) {
		this.m_redirect = false; //always false for JSP pages
		this.m_redirectPath = "/pages/contents/" + jspFile + ".jsp";
	}

	/**
	 * @param controller the redirectPath to set
	 * @param redirect is path need to be redirected
	 * @param parameters is the key paired parameter for the controller
	 */
	public void setRedirectController(final String controller, final boolean redirect, final String parameters) {
		this.m_redirect = redirect;
		if (!AppUtil.isNullOrEmpty(parameters)) {
			this.m_redirectPath = Paths.getPath(controller + ".Servlet") + "?" + parameters;
		} else {
			this.m_redirectPath = Paths.getPath(controller + ".Servlet");
		}
	}

	/**
	 * @param controller the redirectPath to set
	 */
	public void setRedirectController(final String controller) {
		setRedirectController(controller, true);
	}

	/**
	 * @param controller the redirectPath to set
	 * @param redirect is path need to be redirected
	 */
	public void setRedirectController(final String controller, final boolean redirect) {
		setRedirectController(controller, redirect, null);
	}

	/**
	 *
	 * @param statusCode int
	 * @param message String
	 */
	public void setRedirectError(final int statusCode, final String message) {
		m_session.put(WebConst.ATTR_STATUS, statusCode);
		m_session.put(WebConst.ATTR_MESSAGE, message);
		setRedirectController("Error", true);
	}

	/**
	 * @return the m_redirect
	 */
	final boolean isRedirect() {
		return m_redirect;
	}

	/**
	 * @return the request
	 */
	public Request getRequest() {
		return m_request;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return m_session;
	}

	/**
	 * @return the m_out
	 */
	final List<Object> getOut() {
		return m_out;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return m_contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(final String contentType) {
		this.m_contentType = contentType;
	}

	/***/
	public void setOutputAsPDF() {
		this.m_contentType = "application/pdf";
		this.m_printOutput = true;
	}

	/**
	 * @param str String
	 */
	public void println(final String str) {
		m_out.add(str);
		m_out.add(WebConst.NEW_LINE);
	}

	/**
	 * @param str String
	 */
	public void print(final String str) {
		m_out.add(str);
	}

	/**
	 * @param bean ResponseFormBean
	 */
	public void out(final ResponseFormBean bean) {
		m_out.add(bean);
	}

	/***/
	public void clearOut() {
		m_out.clear();
	}

	/**
	 * @return the printOutput
	 */
	public boolean isPrintOutput() {
		return m_printOutput;
	}

	/**
	 * @param printOutput the printOutput to set
	 */
	public void setPrintOutput(final boolean printOutput) {
		this.m_printOutput = printOutput;
	}

	/***/
	public void setRedirectToPreviousPage() {
		this.m_redirect = true;
		this.m_redirectPath = getSession().getAttributeAsString(WebConst.ATTR_PREV_PAGE, "");
	}

	/**
	 * @return the html
	 */
	public String getHtml() {
		return m_html;
	}

	/**
	 * @param html the html to set
	 */
	public void setHtml(final String html) {
		this.m_html = html;
	}

	/**
	 * @return the portrate
	 */
	public boolean isPortrate() {
		return m_portrate;
	}

	/**
	 * @param portrate the portrate to set
	 */
	public void setPortrate(final boolean portrate) {
		this.m_portrate = portrate;
	}

}
