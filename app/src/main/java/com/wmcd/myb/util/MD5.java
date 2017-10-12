package com.wmcd.myb.util;

import java.security.MessageDigest;

/**
 * The type Md 5.
 */
public class MD5 {

	/**
	 * Instantiates a new Md 5.
	 */
	private MD5() {
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		String s = "123456789";
//		System.out.println(s + "____" + getMessageDigest(s.getBytes()));
	}

	/**
	 * Gets message digest.
	 *
	 * @param buffer the buffer
	 * @return the message digest
	 */
	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
