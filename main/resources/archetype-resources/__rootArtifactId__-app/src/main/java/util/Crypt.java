#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author ${author}
 *
 */
public final class Crypt {

	/***/
	public static final String PREFIX = "${parentArtifactId}";

	/***/
    private static javax.crypto.spec.SecretKeySpec keySpec;

	/***/
    private static byte[] byteKey = new byte[] {
    		(byte) 0x01,
    		(byte) 0xE3,
    		(byte) 0xA2,
    		(byte) 0x19,
    		(byte) 0x59,
    		(byte) 0xBD,
    		(byte) 0xEE,
    		(byte) 0xAB
    };

	/***/
    private static String algorithm = "DES";

	/***/
    private Crypt() {

    }

    static {
        keySpec = new javax.crypto.spec.SecretKeySpec(byteKey, algorithm);
    }

    /**
     *
     * @param text String
     * @return byte[]
     */
    public static byte[] encryptString(final String text) {
        try {
            final javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(algorithm);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(text.getBytes());
        } catch (final NoSuchAlgorithmException nsae) {
            return null;
        } catch (final NoSuchPaddingException nspe) {
            return null;
        } catch (final BadPaddingException bpe) {
            return null;
        } catch (final IllegalBlockSizeException ibse) {
            return null;
        } catch (final InvalidKeyException ike) {
            return null;
        }
    }

    /**
     *
     * @param b byte[]
     * @return String
     */
    public static String decryptString(final byte[] b) {
        try {
            final javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(algorithm);
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, keySpec);
            return new String(cipher.doFinal(b));
        } catch (final NoSuchAlgorithmException nsae) {
            return null;
        } catch (final NoSuchPaddingException nspe) {
            return null;
        } catch (final BadPaddingException bpe) {
            return null;
        } catch (final IllegalBlockSizeException ibse) {
            return null;
        } catch (final InvalidKeyException ike) {
            return null;
        }
    }

    /**
     *
     * @param buf byte[]
     * @return String
     */
    public static String toHex(final byte[] buf) {
        final char[] cbf = new char[buf.length * 2];
        int jj = 0;
        int kk = 0;
        for (; jj < buf.length; jj++) {
            cbf[kk++] = "0123456789ABCDEF".charAt((buf[jj] >> 4) & 0x0F);
            cbf[kk++] = "0123456789ABCDEF".charAt(buf[jj] & 0x0F);
        }
        return new String(cbf);
    }

    /**
     *
     * @param hex String
     * @return byte[]
     */
    public static byte[] toByteArray(final String hex) {
        final byte[] result = new byte[hex.length() / 2];
        int jj = 0;
        int kk = 0;
        for (; jj < result.length; jj++) {
            result[jj] = (byte) (("0123456789ABCDEF".indexOf(hex.charAt(kk++)) << 4) + "0123456789ABCDEF"
                    .indexOf(hex.charAt(kk++)));
        }
        return result;
    }

    /**
     *
     * @param text String
     * @return String
     */
    public static String encryptHexString(final String text) {
        return toHex(encryptString(text));
    }

    /**
     *
     * @param text String
     * @return String
     */
    public static String decryptHexString(final String text) {
        return decryptString(toByteArray(text));
    }

}