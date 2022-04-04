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
@DiscriminatorValue(FileType.FileTypeConst.PNG_VALUE)
public class PNGImage extends BaseImage<PNGImage> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PNGImage() {
	}

}
