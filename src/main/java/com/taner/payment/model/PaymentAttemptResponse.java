package com.taner.payment.model;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentAttemptResponse {

    private long id;

    private PaymentResult result;

    private String paymentFailureReason;
}
