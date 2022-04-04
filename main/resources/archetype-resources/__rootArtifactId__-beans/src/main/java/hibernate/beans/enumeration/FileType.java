#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.enumeration;

/**
 * @author ${author}
 *
 */
public enum FileType implements EnumInterface {

	/**
	 * JPEG.
	 */
	JPEG(FileTypeConst.JPEG_VALUE),

	/**
	 * PNG.
	 */
	PNG(FileTypeConst.PNG_VALUE),

	/**
	 * UNKNOWN.
	 */
	UNKNOWN(FileTypeConst.UNKNOWN_VALUE);

	/***/
	private final String m_type;

	/**
	 *
	 * @param type String
	 */
	FileType(final String type) {
		this.m_type = type;
	}

	@Override
	public String getType() {
		return m_type;
	}

	public static FileType getAnnotationType(final String type) {
		switch (type) {
		case FileTypeConst.JPEG_VALUE:
			return JPEG;
		case FileTypeConst.PNG_VALUE:
			return PNG;

		default:
			return UNKNOWN;
		}
	}

	public final class FileTypeConst {
		/**
		 * JPEG.
		 */
		public static final String JPEG_VALUE = ".jpeg";

		/**
		 * PNG.
		 */
		public static final String PNG_VALUE = ".png";

		/**
		 * UNKNOWN.
		 */
		public static final String UNKNOWN_VALUE = "Unknown";

		private FileTypeConst() {

		}
	}

}
