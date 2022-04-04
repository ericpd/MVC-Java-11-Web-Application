#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.filter;

import javax.servlet.DispatcherType;
import javax.servlet.annotation.WebFilter;

import org.apache.logging.log4j.web.Log4jServletFilter;

/**
 * @author ${author}
 */
@WebFilter(filterName = "log4jServletFilter",
urlPatterns = {"/*"},
dispatcherTypes = {
	DispatcherType.REQUEST,
	DispatcherType.FORWARD,
	DispatcherType.INCLUDE,
	DispatcherType.ERROR
})
public final class Log4jFilter extends Log4jServletFilter {
}
