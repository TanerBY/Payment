package com.taner.payment.model;


import lombok.*;

import java.math.BigDecimal;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsResponse {

    private long id;
    private String cardNumber;
    private String cardHolder;
    private String expirationDate;
    private String cvv;
    private BigDecimal amount;
    private String currency;
    private String status;

}
