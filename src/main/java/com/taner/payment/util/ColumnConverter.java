package com.taner.payment.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ColumnConverter implements AttributeConverter<String, String> {
    @Autowired
    CipherMaker encryptionService;
    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            Cipher cipher = encryptionService.getEncryptCipher();
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                 NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            Cipher cipher = encryptionService.getDecryptCipher();
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(dbData)));
        } catch (NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                 NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
