#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.common;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * @author ${author}
 *
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * @param servletRequest HttpServletRequest
	 */
	public XSSRequestWrapper(final HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	public final String[] getParameterValues(final String parameter) {
		final String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}

		final int count = values.length;
		final String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = stripXSS(values[i]);
		}

		return encodedValues;
	}

	@Override
	public final String getParameter(final String parameter) {
		final String value = super.getParameter(parameter);

		return stripXSS(value);
	}

	@Override
	public final String getHeader(final String name) {
		final String value = super.getHeader(name);
		return stripXSS(value);
	}

	/**
	 * @param value String
	 * @return String
	 */
	private String stripXSS(final String value) {
		if (value != null) {
			// NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
			// avoid encoded attacks.
			//value = ESAPI.encoder().canonicalize(value);

			// Avoid null characters
			String newValue = value.replaceAll("", "");

			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			newValue = scriptPattern.matcher(value).replaceAll("");

			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[${symbol_escape}r${symbol_escape}n]*=[${symbol_escape}r${symbol_escape}n]*${symbol_escape}${symbol_escape}${symbol_escape}'(.*?)${symbol_escape}${symbol_escape}${symbol_escape}'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			newValue = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("src[${symbol_escape}r${symbol_escape}n]*=[${symbol_escape}r${symbol_escape}n]*${symbol_escape}${symbol_escape}${symbol_escape}"(.*?)${symbol_escape}${symbol_escape}${symbol_escape}"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			newValue = scriptPattern.matcher(value).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			newValue = scriptPattern.matcher(value).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			newValue = scriptPattern.matcher(value).replaceAll("");

			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval${symbol_escape}${symbol_escape}((.*?)${symbol_escape}${symbol_escape})", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			newValue = scriptPattern.matcher(value).replaceAll("");

			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression${symbol_escape}${symbol_escape}((.*?)${symbol_escape}${symbol_escape})", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			newValue = scriptPattern.matcher(value).replaceAll("");

			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			newValue = scriptPattern.matcher(value).replaceAll("");

			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			newValue = scriptPattern.matcher(value).replaceAll("");

			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			newValue = scriptPattern.matcher(value).replaceAll("");
			return newValue;
		}
		return value;
	}

}
