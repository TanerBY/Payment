package com.taner.payment.service;

import com.taner.payment.model.BankPaymentResponse;
import com.taner.payment.model.Payment;
import com.taner.payment.model.PaymentResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BankSimulator {
    public BankPaymentResponse processPayment(Payment payment) {
        if(payment.getAmount().compareTo(BigDecimal.valueOf(50)) > 0) {
            return BankPaymentResponse.builder().result(PaymentResult.FAIL).paymentFailureReason("Not enough funds!").paymentId(UUID.randomUUID()).build();
        } else {
            return BankPaymentResponse.builder().result(PaymentResult.SUCCESS).paymentId(UUID.randomUUID()).build();
        }
    }
}
