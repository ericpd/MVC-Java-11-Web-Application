#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.data.images;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ${package}.hibernate.beans.base.BaseImage;
import ${package}.hibernate.beans.enumeration.FileType;

/**
 * @author ${author}
 *
 */
@Entity
@DiscriminatorValue(FileType.FileTypeConst.JPEG_VALUE)
public class JPEGImage extends BaseImage<JPEGImage> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public JPEGImage() {
	}

}
