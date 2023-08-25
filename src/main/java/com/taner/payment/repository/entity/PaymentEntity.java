package com.taner.payment.repository.entity;

import com.taner.payment.util.ColumnConverter;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Convert(converter = ColumnConverter.class)
    private String cardNumber;
    @Convert(converter = ColumnConverter.class)
    private String cardHolder;
    @Convert(converter = ColumnConverter.class)
    private String expirationDate;
    @Convert(converter = ColumnConverter.class)
    private String cvv;
    private BigDecimal amount;
    private String currency;
    private String status;
    private UUID paymentId;

}
