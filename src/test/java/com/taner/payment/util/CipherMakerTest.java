package com.taner.payment.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class CipherMakerTest {

    @InjectMocks
    CipherMaker cipherMaker;

    @Test
    public void shouldReturnEnryptCipher () throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = cipherMaker.getEncryptCipher();
        String encryptedText = Base64.getEncoder().encodeToString(cipher.doFinal("Taner".getBytes()));

        assertEquals(encryptedText,"vE06p2TBEkDi6AnWgEWsyw==");
        assertSame(cipher,cipherMaker.getEncryptCipher());
    }

    @Test
    public void shouldReturnDecryptCipher () throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = cipherMaker.getDecryptCipher();
        String encryptedText = new String(cipher.doFinal(Base64.getDecoder()
                .decode("vE06p2TBEkDi6AnWgEWsyw==")));

        assertEquals(encryptedText,"Taner");
        assertSame(cipher,cipherMaker.getDecryptCipher());
    }
}
