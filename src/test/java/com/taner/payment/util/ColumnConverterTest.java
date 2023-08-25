package com.taner.payment.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ColumnConverterTest {

    @Spy
    CipherMaker cipherMaker;

    @InjectMocks
    ColumnConverter columnConverter;

    @Test
    public void shouldEncryptColumn () {
        String convertedVal = columnConverter.convertToDatabaseColumn("taner");
        assertEquals("XN29N6UwPug6ltXyplAk4Q==",convertedVal);
    }

    @Test
    public void shouldDecryptColumn () {
        String convertedVal = columnConverter.convertToEntityAttribute("XN29N6UwPug6ltXyplAk4Q==");
        assertEquals("taner",convertedVal);
    }
}
