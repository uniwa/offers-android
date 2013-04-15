package com.knkcloud.andoid.teiathcoupons.customize;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.content.Context;

import com.knkcloud.andoid.teiathcoupons.utils.Base64;

/**
 * Utility class, manages encryption and Decryption using DES
 * 
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 */
public class EncryptDecrypt {

	Context cont;

	/**
	 * Utility class, manages encryption and Decryption using DES
	 * 
	 * @param context
	 *            the aplication context
	 */
	public EncryptDecrypt(Context context) {

		this.cont = context;

	}

	/**
	 * Encrypts a single string
	 * 
	 * @param plainTextPassword
	 *            the string to be encrypted
	 * @return encrypted string
	 */
	public String Encrypt(String plainTextPassword) {

		SecretKey key;

		try {
			DESKeySpec keySpec = new DESKeySpec("TeiAthOffers".getBytes("UTF8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			key = keyFactory.generateSecret(keySpec);

			byte[] cleartext = plainTextPassword.getBytes("UTF8");

			Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread
														// safe
			cipher.init(Cipher.ENCRYPT_MODE, key);
			String encrypedPwd = Base64.encodeToString(
					cipher.doFinal(cleartext), Base64.DEFAULT);
			return encrypedPwd;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * decrtypts a single string
	 * 
	 * @param encryptedPWd
	 *            the string to be encrypted
	 * @return encrypted string
	 */
	public String Decrypt(String encryptedPWd) {
		SecretKey key;
		String decrypted;

		try {
			DESKeySpec keySpec = new DESKeySpec("TeiAthOffers".getBytes("UTF8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			key = keyFactory.generateSecret(keySpec);

			byte[] encrypedPwdBytes = Base64.decode(encryptedPWd,
					Base64.DEFAULT);

			Cipher cipher = Cipher.getInstance("DES");// cipher is not thread
														// safe
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plainTextPwdBytes = (cipher.doFinal(encrypedPwdBytes));

			String tmp = new String(plainTextPwdBytes);

			decrypted = tmp;
			return decrypted;
		}

		catch (Exception e) {
			return null;
		}

	}

}
