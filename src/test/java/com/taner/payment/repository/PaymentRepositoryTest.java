package com.taner.payment.repository;

import com.taner.payment.repository.entity.PaymentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase
public class PaymentRepositoryTest {
    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void shouldSaveCardDetails () {
        PaymentEntity entity = new PaymentEntity();
        entity.setCardNumber("1234");
        entity.setCardHolder("Taner");
        entity.setCvv("123");
        entity.setExpirationDate("10/10");
        paymentRepository.save(entity);

        PaymentEntity entityDB = paymentRepository.findById(1L).get();

        assertEquals(entity,entityDB);
    }

}
