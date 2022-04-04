#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author ${author}
 *
 */
@ApplicationPath("rest")
public class WebApplication extends ResourceConfig {

	/***/
	public WebApplication() {
		packages(false, "${package}.ui.services");
		register(AuthFilter.class);
	}
}
