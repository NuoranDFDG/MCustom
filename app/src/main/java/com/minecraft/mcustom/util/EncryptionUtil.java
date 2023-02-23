package com.minecraft.mcustom.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apaches.commons.codec.binary.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String IV = "8872991476519635";

    private static final String key = "0T!WE82MxMcaE#y@RG*z!J%6sL*JkB#C";

    public static String encrypt(String text) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes()));
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(encryptedBytes) + ":" + Base64.encodeBase64String(IV.getBytes());
    }

    public static String decrypt(String encryptedText) throws Exception {
        String[] parts = encryptedText.split(":");
        byte[] encryptedBytes = Base64.decodeBase64(parts[0]);
        IvParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(parts[1]));
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

}