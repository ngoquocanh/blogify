package com.quopri.blogify.utils;

import com.quopri.blogify.dto.Payload;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Base64;

/**
 * Util class for encrypt and decrypt data
 * @author Anh Q. NGO (https://github.com/ngoquocanh)
 */
public class CipherHelper {

    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String AES = "AES";
    public static final String UTF8 = "UTF-8";

    /**
     * Encrypt a String
     * @param plainString Plain String
     * @param password    Secret key password
     * @return
     * @throws Exception
     */
    public static String encrypt(String plainString, String password) throws Exception {
        byte[] vector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(vector);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(vector);
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(UTF8), AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] byteToken = cipher.doFinal(plainString.getBytes());
        String strToken = Base64.getEncoder().encodeToString(byteToken);
        Payload payloadBuilder = new Payload(strToken, Hex.toHexString(ivParameterSpec.getIV()));
        String strPayloadBuilder = JacksonJsonUtil.toJson(payloadBuilder);
        return Base64.getEncoder().encodeToString(strPayloadBuilder.getBytes());
    }

    /**
     * Decrypted a String
     * @param encryptedString Encrypted message
     * @param password        Secret key password
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptedString, String password) throws Exception {
        byte[] decodeBytePayloadBuilder = Base64.getDecoder().decode(encryptedString.getBytes());
        String strPayloadBuilder = new String(decodeBytePayloadBuilder, Charset.forName(UTF8));
        Payload payloadBuilder = JacksonJsonUtil.fromJson(strPayloadBuilder, Payload.class);
        byte[] byteToken = Base64.getDecoder().decode(payloadBuilder.getData());
        byte[] vector = Hex.decode(payloadBuilder.getCode());
        IvParameterSpec ivParameterSpec = new IvParameterSpec(vector);
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(UTF8), AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] original = cipher.doFinal(byteToken);
        return new String(original);
    }

    /**
     * Encrypt a String
     * @param plainString Plain String
     * @param password    Secret key password
     * @param salt        Secret key salt
     * @return
     * @throws Exception
     */
    public static String encrypt(String plainString, String password, String salt) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(salt.getBytes(UTF8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(UTF8), AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(plainString.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Decrypted a String
     * @param encryptedString Encrypted message
     * @param password        Secret key password
     * @param salt            Secret key salt
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptedString, String password, String salt) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(salt.getBytes(UTF8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(UTF8), AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
        return new String(original);
    }

    public static void main(String[] args) throws Exception {
//        String plainString = "{email:test@gmail.com,token:8687720a-3862-4044-9a3a-9b1167f4e54a}";
        String plainString = "{\"email\": \"test@gmail.com\",\"token\": \"8687720a-3862-4044-9a3a-9b1167f4e54a\"}";
        String password = "encryptionIntVecencryptionIntVec";
        System.out.println("Original: " + plainString);
        String encrypted = encrypt(plainString, password);
        System.out.println("En: " + encrypted);
        System.out.println("---------------------------");
        String decrypted = decrypt(encrypted, password);
        System.out.println("De: " + decrypted);

    }
}
