package org.csu.input;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

class Test_mine_single {

	Cipher pattern1(PublicKey publicKey, String text) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException {
		Cipher cipher = Cipher.getInstance("RSA");
		try {
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
		} catch (InvalidKeyException e) {
			RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
			RSAPrivateKeySpec spec = new RSAPrivateKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
			Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, fakePrivateKey);
		}
		return cipher;
	}

	Cipher pattern2(PublicKey publicKey, String text) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException {
		Cipher cipher = Cipher.getInstance("RSA");
		try {
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
		} catch (InvalidKeyException e) {
			RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
			RSAPrivateKeySpec spec = new RSAPrivateKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
			Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, fakePrivateKey);
		}
		return cipher;
	}
}