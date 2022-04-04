#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.listener;

import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.web.Log4jServletContextListener;

/**
 *
 * @author ${author}
 *
 */
@WebListener
public class Log4jContextListener extends Log4jServletContextListener {
	//No Content
}
