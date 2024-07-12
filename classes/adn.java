import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class adn {
	private static final Logger a = LogManager.getLogger();

	public static KeyPair b() {
		try {
			KeyPairGenerator keyPairGenerator1 = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator1.initialize(1024);
			return keyPairGenerator1.generateKeyPair();
		} catch (NoSuchAlgorithmException var1) {
			var1.printStackTrace();
			a.error("Key pair generation failed!");
			return null;
		}
	}

	public static byte[] a(String string, PublicKey publicKey, SecretKey secretKey) {
		try {
			return a("SHA-1", string.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
		} catch (UnsupportedEncodingException var4) {
			var4.printStackTrace();
			return null;
		}
	}

	private static byte[] a(String string, byte[]... arr) {
		try {
			MessageDigest messageDigest3 = MessageDigest.getInstance(string);

			for (byte[] arr7 : arr) {
				messageDigest3.update(arr7);
			}

			return messageDigest3.digest();
		} catch (NoSuchAlgorithmException var7) {
			var7.printStackTrace();
			return null;
		}
	}

	public static PublicKey a(byte[] arr) {
		try {
			EncodedKeySpec encodedKeySpec2 = new X509EncodedKeySpec(arr);
			KeyFactory keyFactory3 = KeyFactory.getInstance("RSA");
			return keyFactory3.generatePublic(encodedKeySpec2);
		} catch (NoSuchAlgorithmException var3) {
		} catch (InvalidKeySpecException var4) {
		}

		a.error("Public key reconstitute failed!");
		return null;
	}

	public static SecretKey a(PrivateKey privateKey, byte[] arr) {
		return new SecretKeySpec(b(privateKey, arr), "AES");
	}

	public static byte[] b(Key key, byte[] arr) {
		return a(2, key, arr);
	}

	private static byte[] a(int integer, Key key, byte[] arr) {
		try {
			return a(integer, key.getAlgorithm(), key).doFinal(arr);
		} catch (IllegalBlockSizeException var4) {
			var4.printStackTrace();
		} catch (BadPaddingException var5) {
			var5.printStackTrace();
		}

		a.error("Cipher data failed!");
		return null;
	}

	private static Cipher a(int integer, String string, Key key) {
		try {
			Cipher cipher4 = Cipher.getInstance(string);
			cipher4.init(integer, key);
			return cipher4;
		} catch (InvalidKeyException var4) {
			var4.printStackTrace();
		} catch (NoSuchAlgorithmException var5) {
			var5.printStackTrace();
		} catch (NoSuchPaddingException var6) {
			var6.printStackTrace();
		}

		a.error("Cipher creation failed!");
		return null;
	}

	public static Cipher a(int integer, Key key) {
		try {
			Cipher cipher3 = Cipher.getInstance("AES/CFB8/NoPadding");
			cipher3.init(integer, key, new IvParameterSpec(key.getEncoded()));
			return cipher3;
		} catch (GeneralSecurityException var3) {
			throw new RuntimeException(var3);
		}
	}
}
