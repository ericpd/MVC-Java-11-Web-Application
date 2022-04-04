#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author ${author}
 */
public final class Paths {

    /***/
    private static final String BUNDLE_NAME = "paths"; //${symbol_dollar}NON-NLS-1${symbol_dollar}

    /***/
    private static final ResourceBundle BUNDLE;

    /***/
    private static final List<String> EXACT_PATHS = new ArrayList<>();

    /***/
    private static final List<String> STARTS_WITH_PATHS = new ArrayList<>();

    /***/
    private static final Map<String, String> PATHS = new HashMap<>();

    static {
        BUNDLE = Texts.getBundle(BUNDLE_NAME);
        setAllPaths();
    }

    /***/
    private Paths() {
    }

    /**
	 *
	 */
	private static void setAllPaths() {
		final Set<String> keys = BUNDLE.keySet();
    	for (final String key : keys) {
    		final String path = BUNDLE.getString(key);
    		String pathName = key;
    		if (key.endsWith("Start")) {
    			pathName = pathName.replace(".Start", "");
    			STARTS_WITH_PATHS.add(path);
    		} else if (key.endsWith("Exact")) {
    			pathName = pathName.replace(".Exact", "");
    			EXACT_PATHS.add(path);
    		}
    		PATHS.put(pathName, path);
    	}
	}

	/**
     * @param key String
     * @return String
     */
    public static String getPath(final String key) {
        return PATHS.get(key);
    }

    /**
     * @return List
     */
    public static List<String> getAllExactPaths() {
    	return EXACT_PATHS;
    }

    /**
     * @return List
     */
    public static List<String> getAllStartsWithPaths() {
    	return STARTS_WITH_PATHS;
    }

}