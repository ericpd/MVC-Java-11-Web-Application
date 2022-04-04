#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.taglib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author ${author}
 */
public class CurrentDateTag extends SimpleTagSupport {

    /***/
    private static final SimpleDateFormat SDF = new SimpleDateFormat("MMddyyyyHHmmss");

    @Override
	public final void doTag() throws JspException, IOException {
        final Date date = GregorianCalendar.getInstance().getTime();
        getJspContext().getOut().print(SDF.format(date));
    }

}
