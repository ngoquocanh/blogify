package com.quopri.blogify.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

public class CipherHelper {

    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String AES = "AES";
    public static final String UTF8 = "UTF-8";

    public static String encrypt(String plainString, String password, String salt) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(salt.getBytes(UTF8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(UTF8), AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(plainString.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedString, String password, String salt) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(salt.getBytes(UTF8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(UTF8), AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
        return new String(original);
    }

    public static void main(String[] args) throws Exception {
        String testValue = "{\"email\":\"anhngoquoc@gmail.com\",\"token\":\"8687720a-3862-4044-9a3a-9b1167f4e54a\"}";
        String password = "encryptionIntVecencryptionIntVec";
        String salt = "encryptionIntVec";
        System.out.println("Original: " + testValue);
        System.out.println("En: " + encrypt(testValue,password, salt));
        String decrypted = encrypt(testValue, password, salt);
        System.out.println("De: " + decrypt(decrypted, password, salt));

    }
}
