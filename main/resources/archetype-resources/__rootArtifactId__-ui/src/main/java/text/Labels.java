#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.text;

import java.util.ResourceBundle;

/**
 * @author ${author}
 */
public final class Labels {

    /***/
    private static final String BUNDLE_NAME = "labels"; //${symbol_dollar}NON-NLS-1${symbol_dollar}

    /***/
    private static final ResourceBundle BUNDLE;

    static {
        BUNDLE = Texts.getBundle(BUNDLE_NAME);
    }

    /***/
    private Labels() {
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