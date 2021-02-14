package com.swiftqueue.config.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;

@Slf4j
public class AESDecrypter {

    private static final int KEY_LENGTH = 32;
    private static final int IV_LENGTH = 16;

    public String decrypt(String secret, String cipherText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, DigestException {
        byte[] cipherData = Base64.decodeBase64(cipherText);
        byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        final byte[][] keyAndIV = generateKeyAndIV(saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
        SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
        IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

        byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
        Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedData = aesCBC.doFinal(encrypted);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    private byte[][] generateKeyAndIV(byte[] salt, byte[] password, MessageDigest md) throws DigestException {

        int digestLength = md.getDigestLength();
        int requiredLength = (AESDecrypter.KEY_LENGTH + AESDecrypter.IV_LENGTH + digestLength - 1) / digestLength * digestLength;
        byte[] generatedData = new byte[requiredLength];
        int generatedLength = 0;
        try {
            md.reset();
            // Repeat process until sufficient data has been generated
            while (generatedLength < AESDecrypter.KEY_LENGTH + AESDecrypter.IV_LENGTH) {
                // Digest data (last digest if available, password data, salt if available)
                if (generatedLength > 0)
                    md.update(generatedData, generatedLength - digestLength, digestLength);
                md.update(password);
                if (salt != null)
                    md.update(salt, 0, 8);
                md.digest(generatedData, generatedLength, digestLength);
                generatedLength += digestLength;
            }
            // Copy key and IV into separate byte arrays
            byte[][] result = new byte[2][];
            result[0] = Arrays.copyOfRange(generatedData, 0, AESDecrypter.KEY_LENGTH);
            if (AESDecrypter.IV_LENGTH > 0)
                result[1] = Arrays.copyOfRange(generatedData, AESDecrypter.KEY_LENGTH, AESDecrypter.KEY_LENGTH + AESDecrypter.IV_LENGTH);
            return result;
        } catch (DigestException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            // Clean out temporary data
            Arrays.fill(generatedData, (byte)0);
        }
    }
}
