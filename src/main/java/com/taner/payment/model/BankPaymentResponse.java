package com.taner.payment.model;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class BankPaymentResponse {

    private UUID paymentId;

    private PaymentResult result;

    private String paymentFailureReason;


}
