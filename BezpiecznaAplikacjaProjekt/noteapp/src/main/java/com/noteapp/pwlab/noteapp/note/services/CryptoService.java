package com.noteapp.pwlab.noteapp.note.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class CryptoService {
    @Value("s4lT")
	private String salt;


	private SecretKey getKey(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		return new SecretKeySpec(tmp.getEncoded(), "AES");
	}

	public String encrypt(String key, String value) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		IvParameterSpec bytesSpec = new IvParameterSpec(new byte[16]);
		cipher.init(Cipher.ENCRYPT_MODE, getKey(key), bytesSpec);

		return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes(StandardCharsets.UTF_8)));
	}

	public String decrypt(String key, String toDecrypt) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		byte[] encrypted = Base64.getDecoder().decode(toDecrypt);

		IvParameterSpec bytesSpec = new IvParameterSpec(new byte[16]);
		cipher.init(Cipher.DECRYPT_MODE, getKey(key), bytesSpec);

		String decrypted = new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
		return decrypted;
	}
}
