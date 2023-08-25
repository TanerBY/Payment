package com.taner.payment.util;

import com.taner.payment.model.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PaymentValidatorTest {

    @InjectMocks
    PaymentValidator paymentValidator;

    @Test
    public void shouldValidatePayment () {
        paymentValidator.validatePayment(Payment.builder().amount(BigDecimal.TEN).currency("GBP").build());
    }

    @Test
    public void shouldThrowExceptionForInvalidCurrency () {
        assertThrows(RuntimeException.class,
                ()->{
                    paymentValidator.validatePayment(Payment.builder().amount(BigDecimal.TEN).currency("TNR").build());} );
    }

    @Test
    public void shouldThrowExceptionForInvalidAmount () {
        assertThrows(RuntimeException.class,
                ()->{
                    paymentValidator.validatePayment(Payment.builder().amount(BigDecimal.valueOf(0)).currency("GBP").build());} );
    }


}
