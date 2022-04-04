#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.formdatas;

import java.io.Serializable;

/**
 *
 * @author ${author}
 *
 */
public class UserPhotoFormData implements Serializable {

	private static final long serialVersionUID = 1L;

	/***/
	private String m_id;

	/***/
	private String m_image;

	/**
	 * @return the id
	 */
	public String getId() {
		return this.m_id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.m_id = id;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return this.m_image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(final String image) {
		this.m_image = image;
	}


}
