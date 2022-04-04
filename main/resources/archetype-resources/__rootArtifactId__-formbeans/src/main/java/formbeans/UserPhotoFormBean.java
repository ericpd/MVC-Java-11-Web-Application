#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.formbeans;

/**
 *
 * @author ${author}
 *
 */
public class UserPhotoFormBean extends UserFormBean {

	private static final long serialVersionUID = 1L;

	/***/
	private String m_id;

	/***/
	private String m_image;

	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return this.m_id;
	}

	/**
	 * @param id the id to set
	 * @return  UserFormBean
	 */
	@Override
	public UserFormBean setId(final String id) {
		this.m_id = id;
		return this;
	}

	/**
	 * @return the image
	 */
	@Override
	public String getImage() {
		return this.m_image;
	}

	/**
	 * @param image the image to set
	 * @return  UserFormBean
	 */
	@Override
	public UserFormBean setImage(final String image) {
		this.m_image = image;
		return this;
	}


}
