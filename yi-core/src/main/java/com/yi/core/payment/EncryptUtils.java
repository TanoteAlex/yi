package com.yi.core.payment;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * 加密工具类
 * 
 * @author xuyh
 *
 */
public class EncryptUtils {

	/** 字符集 */
	private static final String CHARSET_UTF8 = "UTF-8";

	/** 算法 MD5 */
	private static final String ALGORITHM_MD5 = "MD5";

	/** 算法 SHA1 */
	private static final String ALGORITHM_SHA1 = "SHA1";

	/** 算法 DES */
	private static final String ALGORITHM_DES = "DES";

	private static Cipher desCipher;
	private static SecretKey desGenerateKey;

	private static Cipher _3desCipher;
	private static SecretKey _3desGenerateKey;

	private static Cipher aesCipher;
	private static SecretKey aesGenerateKey;

	private static Cipher pbeCipher;
	private static SecretKey pbeGenerateKey;
	private static PBEParameterSpec pbePbeParameterSpec;

	private static RSAPublicKey rsaPublicKey;
	private static RSAPrivateKey rsaPrivateKey;

	private static Cipher dhCipher;
	private static SecretKey dhReceiverSecretKey;

	/**
	 * BASE64 加密
	 * 
	 * @param source
	 * @return
	 */
	public String base64Encode(String source) {
		byte[] encodeBytes = Base64.getEncoder().encode(source.getBytes());
		return new String(encodeBytes);
	}

	/**
	 * BASE64 解密
	 * 
	 * @param source
	 * @return
	 */
	public String base64Decode(String source) {
		byte[] decodeBytes = Base64.getDecoder().decode(source.getBytes());
		return new String(decodeBytes);
	}

	/**
	 * 消息摘要算法 MD5 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String md5Encode(String source) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
			byte[] encodeBytes = md.digest(source.getBytes(CHARSET_UTF8));
			return Hex.encodeHexString(encodeBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 消息摘要算法 MD5 解密
	 * 
	 * @param source
	 * @return
	 */
	public static String md5Decode(String source) {
		throw new RuntimeException("MD5 no decode");
	}

	/**
	 * 安全散列算法 SHA1 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String sha1Encode(String source) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM_SHA1);
			md.update(source.getBytes(CHARSET_UTF8));
			return Hex.encodeHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 安全散列算法 SHA1 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String sha1Decode(String source) {
		throw new RuntimeException("SHA no decode");
	}

	/**
	 * 对称加密 DES 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String desEncode(String source) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_DES);
			keyGenerator.init(56);// size
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();

			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM_DES);
			desGenerateKey = secretKeyFactory.generateSecret(desKeySpec);

			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			desCipher.init(Cipher.ENCRYPT_MODE, desGenerateKey);
			byte[] resultBytes = desCipher.doFinal(source.getBytes());

			return Hex.encodeHexString(resultBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 对称加密 DES 解密
	 * 
	 * @param source
	 * @return
	 */
	public static String desDecode(String source) {
		try {
			desCipher.init(Cipher.DECRYPT_MODE, desGenerateKey);
			byte[] result = Hex.decodeHex(source.toCharArray());
			return new String(desCipher.doFinal(result));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 三重数据加密算法 3DES 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String _3desEncode(String source) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
			keyGenerator.init(168);// size
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();

			DESedeKeySpec desKeySpec = new DESedeKeySpec(keyBytes);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
			_3desGenerateKey = secretKeyFactory.generateSecret(desKeySpec);
			_3desCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			_3desCipher.init(Cipher.ENCRYPT_MODE, _3desGenerateKey);
			byte[] resultBytes = _3desCipher.doFinal(source.getBytes());
			return Hex.encodeHexString(resultBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 三重数据加密算法 3DES 解密
	 * 
	 * @param source
	 * @return
	 */
	public static String _3desDecode(String source) {
		try {
			_3desCipher.init(Cipher.DECRYPT_MODE, _3desGenerateKey);
			byte[] result = Hex.decodeHex(source.toCharArray());
			return new String(_3desCipher.doFinal(result));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 高级加密标准 AES 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String aesEncode(String source) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);// size
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			aesGenerateKey = new SecretKeySpec(keyBytes, "AES");
			aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			aesCipher.init(Cipher.ENCRYPT_MODE, aesGenerateKey);
			byte[] resultBytes = aesCipher.doFinal(source.getBytes());
			return Hex.encodeHexString(resultBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 高级加密标准 AES 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String aesDecode(String source) {
		try {
			aesCipher.init(Cipher.DECRYPT_MODE, aesGenerateKey);
			byte[] result = Hex.decodeHex(source.toCharArray());
			return new String(aesCipher.doFinal(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 基于口令加密 PBE（password），对称 + 消息摘要
	 * 
	 * @param source
	 * @return
	 */
	public static String pbeEncode(String source) {
		try {
			SecureRandom secureRandom = new SecureRandom();
			byte[] salt = secureRandom.generateSeed(8);

			String password = "pbepwd";
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			pbeGenerateKey = secretKeyFactory.generateSecret(pbeKeySpec);

			pbePbeParameterSpec = new PBEParameterSpec(salt, 100);
			pbeCipher = Cipher.getInstance("PBEWITHMD5andDES");
			pbeCipher.init(Cipher.ENCRYPT_MODE, pbeGenerateKey, pbePbeParameterSpec);
			byte[] resultBytes = pbeCipher.doFinal(source.getBytes());
			return Hex.encodeHexString(resultBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 基于口令加密 PBE（password），对称 + 消息摘要
	 * 
	 * @param source
	 * @return
	 */
	public static String pbeDecode(String source) {
		try {
			pbeCipher.init(Cipher.DECRYPT_MODE, pbeGenerateKey, pbePbeParameterSpec);
			byte[] result = Hex.decodeHex(source.toCharArray());
			return new String(pbeCipher.doFinal(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 非对称加密 RSA加密
	 * 
	 * @param source
	 * @return
	 */
	public static String rsaEncode(String source) {
		try {
			// 初始化密钥
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

			// 私钥加密 公钥解密
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] resultBytes = cipher.doFinal(source.getBytes());

			// 私钥解密 公钥加密
			// X509EncodedKeySpec x509EncodedKeySpec =
			// new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			// KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			// Cipher cipher = Cipher.getInstance("RSA");
			// cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			// byte[] resultBytes = cipher.doFinal(source.getBytes());

			return Hex.encodeHexString(resultBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 非对称加密 RSA解密
	 * 
	 * @param source
	 * @return
	 */
	public static String rsaDecode(String source) {
		try {
			// 私钥加密 公钥解密
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] resultBytes = cipher.doFinal(Hex.decodeHex(source.toCharArray()));

			// 私钥解密 公钥加密
			// PKCS8EncodedKeySpec pkcs8EncodedKeySpec
			// = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			// KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			// Cipher cipher = Cipher.getInstance("RSA");
			// cipher.init(Cipher.DECRYPT_MODE, privateKey);
			// byte[] resultBytes = cipher.doFinal(Hex.decodeHex(source.toCharArray()));

			return new String(resultBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DH 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String dhEncode(String source) {
		try {
			// 初始化发送方密钥
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			senderKeyPairGenerator.initialize(512);
			KeyPair senderkeyPair = senderKeyPairGenerator.generateKeyPair();
			PrivateKey senderPrivateKey = senderkeyPair.getPrivate();
			byte[] senderPublicKeyBytes = senderkeyPair.getPublic().getEncoded();// 发送方的公钥

			// 初始化接收方密钥,用发送方的公钥
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyBytes);
			PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
			DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
			byte[] receiverPublicKeyBytes = receiverKeyPair.getPublic().getEncoded();

			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(receiverPublicKey, true);
			dhReceiverSecretKey = receiverKeyAgreement.generateSecret("DES");

			// 发送方拿到接收方的public key就可以做加密了
			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyBytes);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderPrivateKey);
			senderKeyAgreement.doPhase(senderPublicKey, true);
			SecretKey senderSecretKey = senderKeyAgreement.generateSecret("DES");

			if (Objects.equals(dhReceiverSecretKey, senderSecretKey)) {
				dhCipher = Cipher.getInstance("DES");
				dhCipher.init(Cipher.ENCRYPT_MODE, senderSecretKey);
				byte[] result = dhCipher.doFinal(source.getBytes());
				return Hex.encodeHexString(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DH 解密
	 * 
	 * @param source
	 * @return
	 */
	public static String dhDecode(String source) {
		try {
			dhCipher.init(Cipher.DECRYPT_MODE, dhReceiverSecretKey);
			byte[] result = Hex.decodeHex(source.toCharArray());
			return new String(dhCipher.doFinal(result));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 数字签名证书
	 * 
	 * @param source
	 * @return
	 */
	public static boolean verifySign(String source) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			PublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			PrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(privateKey);
			signature.update(source.getBytes());
			// 生成签名bytes
			byte[] signBytes = signature.sign();

			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(publicKey);
			signature.update(source.getBytes());
			return signature.verify(signBytes);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
