#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.text.WordUtils;

import ${package}.hibernate.beans.base.BaseUser;

/**
 * @author ${author}
 *
 */
public final class AppUtil {

	/***/
	private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("MMM dd, yyyy");

	/***/
	private static final SimpleDateFormat SDF_LONG_DATE = new SimpleDateFormat("MMMMM dd, yyyy");

	/***/
	private static final SimpleDateFormat SDF_MONTH_YEAR = new SimpleDateFormat("MMM yyyy");

	/***/
	public static final ZoneId DEFAULT_ZONE = ZoneId.of(System.getProperty("ZONE", "Singapore"));

	/***/
	public static final DecimalFormat NUM_DISPLAY_FORMATTER = new DecimalFormat("${symbol_pound},${symbol_pound}${symbol_pound}${symbol_pound}.00");

	/***/
	private AppUtil() {
	}

	/**
	 *
	 * @param value String
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(final String value) {
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
	 * @param value List
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(final List value) {
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
	 * @param password String
	 * @return String
	 */
	public static String encyptPassword(final String password) {
		return Crypt.encryptHexString(Crypt.PREFIX + password);
	}

	/**
	 *
	 * @param encrptedPassword String
	 * @return String
	 */
	public static String decryptPassword(final String encrptedPassword) {
		return Crypt.decryptHexString(encrptedPassword).replace(Crypt.PREFIX, "");
	}

	/**
	 * @param dateMillis long
	 * @return Date
	 */
	public static Date getDateFromMillis(final long dateMillis) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateMillis);
		return calendar.getTime();
	}

	/**
	 * @return LocalDate
	 */
	public static LocalDate getLocalDate() {
		return LocalDate.now(DEFAULT_ZONE);
	}

	/**
	 * @return LocalTime
	 */
	public static LocalTime getLocalTime() {
		return LocalTime.now(DEFAULT_ZONE);
	}

	public static Calendar getCalendarInstance() {
		return Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
	}

	/**
	 * @return String
	 */
	public static String getCurrentDisplayDate() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		return SDF_DATE.format(calendar.getTime());
	}

	/**
	 * @return String
	 */
	public static String getCurrentDisplayMonthYear() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		return SDF_MONTH_YEAR.format(calendar.getTime());
	}

	/**
	 * @return Date
	 */
	public static Date getCurrentDate() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		return calendar.getTime();
	}

	/**
	 * @return Date
	 */
	public static int getCurrentMonth() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		return calendar.get(Calendar.MONTH) + 1; //  plus one for regular month
	}

	/**
	 * @return Date
	 */
	public static int getCurrentDay() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return Date
	 */
	public static int getCurrentYear() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * @param date as date param
	 * @return Date
	 */
	public static int getMonth(final Date date) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1; //  plus one for regular month
	}

	/**
	 * @param date as date param
	 * @return Date
	 */
	public static int getDay(final Date date) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param date as date param
	 * @return Date
	 */
	public static int getYear(final Date date) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * @param user BaseUser
	 * @return String
	 */
    @SuppressWarnings("rawtypes")
	public static String getFullName(final BaseUser user) {
        return WordUtils.capitalize(user.getFirstName()) + " " + WordUtils.capitalize(user.getLastName());
    }

//	/**
//	 * @param firstName String
//	 * @param lastName String
//	 * @return String
//	 */
//    public static String getFullName(final String firstName, final String lastName) {
//        return WordUtils.capitalize(firstName) + " " + WordUtils.capitalize(lastName);
//    }

    /**
     * @param value String
     * @return String
     */
    public static String upperCaseFirstLetter(final String value) {
        final String cap = value.substring(0, 1).toUpperCase();
        return cap + value.substring(1).toLowerCase();
    }

	/**
	 * @param value String
	 * @return String
	 */
	public static String decodeBase64(final String value) {
		final String suffix = "${parentArtifactId}_suf";
		final byte[] decoded = Base64.getDecoder().decode(value.getBytes(Charset.forName("UTF8")));
		return new String(decoded, Charset.forName("UTF8")).replace(suffix, "");
	}

	/**
	 * @param value String
	 * @return String
	 */
	public static String encodeBase64(final String value) {
		final String toEncode = value + "${parentArtifactId}_suf";
		return Base64.getEncoder().encodeToString(toEncode.getBytes(Charset.forName("UTF8")));
	}

	/**
	 * @param addressName String
	 * @param city String
	 * @param country String
	 * @param zipCode String
	 * @return String
	 */
	public static String getFullAddress(final String addressName,
										final String city,
										final String country,
										final String zipCode) {
		return addressName +
				appendPrefixIfNotNullOrEmpty(city, ", ") +
				appendPrefixIfNotNullOrEmpty(country, ", ") +
				appendPrefixIfNotNullOrEmpty(zipCode, " ");
	}

	/**
	 * @param value String
	 * @param prefix String
	 * @return String
	 */
	public static String appendPrefixIfNotNullOrEmpty(final String value, final String prefix) {
		if (isNullOrEmpty(value)) {
			return "";
		}
		return prefix + value;
	}

	/**
	 * @param value String
	 * @param suffix String
	 * @return String
	 */
	public static String appendSuffixIfNotNullOrEmpty(final String value, final String suffix) {
		if (isNullOrEmpty(value)) {
			return "";
		}
		return value + suffix;
	}

	/**
	 *
	 * @param date Date
	 * @param days int
	 * @return Date
	 */
	public static Date addDays(final Date date, final int days) {
		final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
	}

	/**
	 *
	 * @param date Date
	 * @return Date
	 */
	public static Date addTimeBeforeMidnight(final Date date) {
		final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        return cal.getTime();
	}

	/**
	 *
	 * @param date Date
	 * @param months int
	 * @return Date
	 */
	public static Date addMonths(final Date date, final int months) {
		final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months); //minus number would decrement the days
        return cal.getTime();
	}

	/**
	 *
	 * @param date Date
	 * @param years int
	 * @return Date
	 */
	public static Date addYears(final Date date, final int years) {
		final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years); //minus number would decrement the days
        return cal.getTime();
	}

	public static boolean disableSystemUser(final boolean systemUser) {
		if (!systemUser) {
			return false;
		}

		return !SystemSettingsUtil.getSettingAsBool("system.user.enable");
	}

	/**
	 *
	 * @param expiryDate Date
	 * @return boolean
	 */
	public static boolean isExpired(final Date expiryDate) {
		if (null == expiryDate) {
			return false;
		}

		final Date newExpiryDate = addTimeBeforeMidnight(expiryDate);

		return getCurrentDate().after(newExpiryDate);
	}

	/**
	 *
	 * @param date Date
	 * @return string date
	 */
	public static String getDisplayDate(final Date date) {
		if (null == date) {
			return null;
		}
		return SDF_DATE.format(date);
	}

	public static String getDefaultCountry() {
		return "Philippines";
	}

	/**
	 *
	 * @param date Date
	 * @return String
	 */
	public static String getLongDateStr(final Date date) {
		return SDF_LONG_DATE.format(date);
	}

	/**
	 *
	 * @param date Date
	 * @return String
	 */
	public static String getCutoffDate(final Date date) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		final SimpleDateFormat sdf = new SimpleDateFormat("MMMM 1-dd, yyyy");
		return sdf.format(calendar.getTime());
	}

	/**
	 * @param date Date
	 * @return String
	 */
	public static String getStringMonthYear(final Date date) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_ZONE));
		calendar.setTime(date);
		return SDF_MONTH_YEAR.format(calendar.getTime());
	}

	/**
	 *
	 * @param value double
	 * @return String
	 */
	public static String getTwoDecimalDisplay(final double value) {
		return NUM_DISPLAY_FORMATTER.format(value);
	}

	/**
	 *
	 * @param date Date
	 * @return boolean
	 */
	public static boolean isCurrentMonth(final Date date) {
		if (null == date) {
			return false;
		}
		return (getYear(date) == getCurrentYear() && getMonth(date) == getCurrentMonth());
	}

}
