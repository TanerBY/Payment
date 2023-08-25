package com.taner.payment.util;


import com.taner.payment.model.Payment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;

@Component
public class PaymentValidator {

    public void validatePayment(Payment payment) {
        validateAmount(payment.getAmount());
        validateCurrency(payment.getCurrency());
    }

    private void validateAmount(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new RuntimeException("The amount of payment has to be bigger than 0");
        }
    }

    private void validateCurrency(String currency) {
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Currency code is not supported!");
        } catch (NullPointerException e) {
            throw new RuntimeException("Currency code can not be left empty");
        }
    }

}
