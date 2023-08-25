package com.taner.payment.service;

import com.taner.payment.model.*;
import com.taner.payment.repository.PaymentRepository;
import com.taner.payment.repository.entity.PaymentEntity;
import com.taner.payment.util.PaymentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    ModelMapper modelMapper;

    @Mock
    PaymentValidator paymentValidator;

    @Mock
    BankSimulator bankSimulator;

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentService paymentService;

    @Test
    public void shouldProcessPayment () {

        Payment payment = Payment.builder().cardHolder("Taner").cardNumber("123").cvv("1234").expirationDate("10/10").build();
        UUID paymentID = UUID.randomUUID();
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(1);
        paymentEntity.setPaymentId(paymentID);
        paymentEntity.setStatus(PaymentResult.SUCCESS.name());

        when(modelMapper.map(payment, PaymentEntity.class)).thenReturn(paymentEntity);

        when(bankSimulator.processPayment(payment)).thenReturn(BankPaymentResponse.builder().paymentId(paymentID).result(PaymentResult.SUCCESS).build());
        when(paymentRepository.save(paymentEntity)).thenReturn(paymentEntity);

        PaymentAttemptResponse paymentResponse = paymentService.processPayment(payment);

        Mockito.verify(paymentRepository).save(paymentEntity);
        assertEquals(paymentResponse.getId(), 1);
        assertEquals(paymentResponse.getResult(), PaymentResult.SUCCESS);
        assertEquals(paymentResponse.getPaymentFailureReason(), null);
    }

    @Test
    public void shouldGetPaymentDetails () {

        PaymentDetailsResponse paymentDetailsResponse = PaymentDetailsResponse.builder().cardHolder("Taner").cardNumber("1234123412341234").cvv("1234").expirationDate("10/10").build();
        PaymentEntity paymentEntity = new PaymentEntity();
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(paymentEntity));
        when(modelMapper.map(paymentEntity, PaymentDetailsResponse.class)).thenReturn(paymentDetailsResponse);
        paymentDetailsResponse.setCardNumber("1234XXXXXXXX1234");
        PaymentDetailsResponse payment = paymentService.getPayment(1L);
        assertEquals(payment, paymentDetailsResponse);
    }

    @Test
    public void shouldGetExceptionIfCannotFindPayment () {

        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                ()->{
                    paymentService.getPayment(1L);} );

    }


}
