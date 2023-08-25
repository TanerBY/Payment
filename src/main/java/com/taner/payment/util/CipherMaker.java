package com.taner.payment.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class CipherMaker {

    private static final String SECRET_KEY = "secretkey1234567";

    private static Cipher encryptCipher = null;
    private static Cipher decryptCipher = null;

    public Cipher getEncryptCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if( encryptCipher == null) {
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptCipher = cipher;
            return cipher;
        } else {
            return encryptCipher;
        }
    }

    public Cipher getDecryptCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if( decryptCipher == null) {
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptCipher = cipher;
            return cipher;
        } else {
            return decryptCipher;
        }
    }

}