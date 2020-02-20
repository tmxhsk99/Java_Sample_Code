/**
 * @brief
 * @Detail
 */
package myjava.sample.blockchain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class StringUtil {
	
	public static String applySha256(String input){
		MessageDigest md;
		String sha256HexString = ""; 
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(input.getBytes("UTF-8"));
			byte[] hash = md.digest();
			sha256HexString = bytesToHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		return sha256HexString;
	}
	
	public static String bytesToHexString(byte[] bytes){		
		  final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	        char[] hexChars = new char[bytes.length * 2];
	        int v;
	        for ( int j = 0; j < bytes.length; j++ ) {
	            v = bytes[j] & 0xFF;
	            hexChars[j * 2] = hexArray[v >>> 4];
	            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	        }
	        return new String(hexChars);

	}
}


