#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.generators;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author ${author}
 *
 */
public final class RandomGenerator {

	/***/
	private RandomGenerator() {
	}

	/**
	 *
	 * @return String
	 */
	public static String getRandString() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	/**
	 *
	 * @param num int
	 * @return String
	 */
	public static String randNChar(final int num) {
		String randVal = getRandString();
		while (randVal.length() < num) {
			randVal = randVal + getRandString();
		}
		return randVal.substring(0, num - 1);
	}

	/**
	 *
	 * @return String
	 */
	public static String rand12Char() {
		final String randVal = getRandString();
		return randVal.substring(0, 11);
	}

	/**
	 *
	 * @return String
	 */
	public static String rand14Char() {
		final String randVal = getRandString();
		return randVal.substring(0, 13);
	}

	/**
	 *
	 * @return String
	 */
	public static String rand8Char() {
		final String randVal = getRandString();
		return randVal.substring(0, 7);
	}

	/**
	 *
	 * @return String
	 */
	public static String rand2Char() {
		final String randVal = getRandString();
		return randVal.substring(0, 1);
	}

	/**
	 *
	 * @return String
	 */
	public static long rand8Number() {
		final Random rand = new Random();
		return 10000000 + rand.nextInt(89999999);
	}

	/**
	 *
	 * @return String
	 */
	public static long rand3Number() {
		final Random rand = new Random();
		return 100 + rand.nextInt(899);
	}

	/**
	 *
	 * @return String
	 */
	public static long rand4Number() {
		final Random rand = new Random();
		return 1000 + rand.nextInt(8999);
	}

	/**
	 *
	 * @return String
	 */
	public static long rand5Number() {
		final Random rand = new Random();
		return 10000 + rand.nextInt(89999);
	}

	/**
	 *
	 * @return String
	 */
	public static long rand6Number() {
		final Random rand = new Random();
		return 100000 + rand.nextInt(899999);
	}

}
