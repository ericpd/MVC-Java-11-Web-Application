#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.component;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.exception.ComponentException;
import ${package}.hibernate.beans.base.BaseImage;
import ${package}.hibernate.beans.data.images.JPEGImage;
import ${package}.hibernate.beans.data.images.PNGImage;
import ${package}.hibernate.beans.enumeration.FileType;
import ${package}.hibernate.dao.GenericDao;

/**
 * @author ${author}
 *
 */
class BaseComponent {

	/***/
	private final Logger m_logger;

	/**
	 * @param clazz Class< ? extends BaseComponent >
	 */
	protected BaseComponent(final Class< ? extends BaseComponent > clazz) {
		m_logger = LogManager.getLogger(clazz);
	}

	/**
	 * @param value long
	 * @return String
	 */
	protected String stringValueOf(final long value) {
		return String.valueOf(value);
	}

	/**
	 * @param value int
	 * @return String
	 */
	protected String stringValueOf(final int value) {
		return String.valueOf(value);
	}

	/**
	 * @param value String
	 * @return String
	 */
	protected String nullToEmpty(final String value) {
		if (null == value) {
			return "";
		}
		return value;
	}

	/**
	 * @param value String
	 * @return boolean
	 */
	protected boolean isNullOrEmpty(final String value) {
		if (null == value) {
			return true;
		}

		if (value.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 *
	 * @param currVal String
	 * @param newVal String
	 * @return boolean
	 */
	protected boolean hasChange(final String currVal, final String newVal) {
		if (null != newVal && newVal.equals(currVal)) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param currVal Date
	 * @param newVal Date
	 * @return boolean
	 */
	protected boolean hasChange(final Date currVal, final Date newVal) {
		if (newVal == null) {
			return false;
		}

		if (null == currVal && null != newVal) {
			return true;
		}

		if (null != currVal && null == newVal) {
			return true;
		}

		if (newVal.compareTo(currVal) != 0) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param currVal double
	 * @param newVal double
	 * @return boolean
	 */
	protected boolean hasChange(final double currVal, final double newVal) {
		if (newVal == currVal) {
			return false;
		}
		return true;
	}

	/**
	 * @param value Date
	 * @return String
	 */
	protected String toMillisSec(final Date value) {
		if (null == value) {
			return null;
		}
		return stringValueOf(value.getTime());
	}

	/**
	 * @param value Date
	 * @return String
	 */
	protected long toMillis(final Date value) {
		if (null == value) {
			return 0L;
		}
		return value.getTime();
	}

	/**
	 * @return Logger
	 */
	protected Logger getLogger() {
		return m_logger;
	}

	/**
	 * @param list List
	 * @param type Type
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	protected String toGson(final List list, final Type type) {
		final Gson gson = new Gson();
		final String data = gson.toJson(list, type);
		return data;
	}

	/**
	 *
	 * @param data String
	 * @param type FileType
	 * @param dao GenericDao
	 * @return BaseImage
	 * @throws AppException
	 */
	@SuppressWarnings({ "rawtypes" })
	protected BaseImage newImage(final String data, final FileType type, final GenericDao dao) throws ComponentException {
		BaseImage image;
		switch (type) {
		case JPEG:
			image = new JPEGImage().setData(data.getBytes());
			break;
		case PNG:
			image = new PNGImage().setData(data.getBytes());
			break;

		default:
			throw new ComponentException("Data error.", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
		}

		final GenericDao<BaseImage> imgDao = new GenericDao<>(BaseImage.class, dao);
		imgDao.save(image);

		return image;
	}

	/**
	 *
	 * @param image BaseImage
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	protected String getImage(final BaseImage image) {
		if (null == image) {
			return null;
		}
		return image.toString();
	}

	protected String truncateAndAppend(final String title, final int length, final String strToAppend) {
		String newStr = title;
		if (title.length() > length) {
			final int lastSpace = title.substring(0, length - strToAppend.length()).lastIndexOf(' ');
			if (lastSpace <= 0) {
				newStr = title.substring(0, length - strToAppend.length()) + strToAppend;
			} else {
				newStr =  title.substring(0, lastSpace) + strToAppend;
			}
		}
		return newStr;
	}

}
