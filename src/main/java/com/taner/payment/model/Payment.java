package com.taner.payment.model;


import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Valid
@Builder
public class Payment {

    @NotEmpty(message = "Card number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String cardNumber;

    @NotEmpty(message = "Card holder is required")
    private String cardHolder;

    @NotEmpty(message = "Expiration date is required")
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Expiration date must be in the format MM/YY")
    private String expirationDate;

    @NotEmpty(message = "CVV is required")
    @Size(min = 3, max = 3, message = "CVV must be 3 digits")
    private String cvv;

    private BigDecimal amount;

    private String currency;

}
