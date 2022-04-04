#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.text;

import java.util.ResourceBundle;

/**
 * @author ${author}
 */
public final class Messages {

    /***/
    private static final String BUNDLE_NAME = "messages";

    /***/
    private static final ResourceBundle BUNDLE = Texts.getBundle(BUNDLE_NAME);

    /***/
    private Messages() {
    }

    /**
     * @param key String
     * @return String
     */
    public static String getString(final String key) {
        return Texts.getString(BUNDLE, key);
    }

    /**
     * @param key String
     * @param arg Object
     * @return String
     */
    public static String getString(final String key, final Object arg) {
        return Texts.getString(BUNDLE, key, arg);
    }

    /**
     * @param key String
     * @param args String[]
     * @return String
     */
    public static String getString(final String key, final String[] args) {
        return Texts.getString(BUNDLE, key, args);
    }
}