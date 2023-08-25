package com.taner.payment.service;

import com.taner.payment.model.*;
import com.taner.payment.repository.PaymentRepository;
import com.taner.payment.repository.entity.PaymentEntity;
import com.taner.payment.util.PaymentValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class PaymentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PaymentValidator paymentValidator;

    @Autowired
    BankSimulator bankSimulator;

    @Autowired
    PaymentRepository paymentRepository;

    public PaymentAttemptResponse processPayment(Payment payment) {

        paymentValidator.validatePayment(payment);

        BankPaymentResponse bankPaymentResponse = bankSimulator.processPayment(payment);

        PaymentEntity paymentEntity = prepareAndSavePaymentAttempt(bankPaymentResponse,payment);

        return prepareResponse(bankPaymentResponse, paymentEntity);

    }

    public PaymentDetailsResponse getPayment(Long id) {

        PaymentDetailsResponse response = paymentRepository.findById(id).map(i -> modelMapper.map(i, PaymentDetailsResponse.class)).orElseThrow(() ->new ResponseStatusException(NOT_FOUND, "Payment info not found"));

        return maskCCNumber(response);
    }

    private PaymentDetailsResponse maskCCNumber (PaymentDetailsResponse response) {
        String replaced = response.getCardNumber().replaceAll("\\b(\\d{4})(\\d{8})(\\d{4})", "$1XXXXXXXX$3");
        response.setCardNumber(replaced);
        return response;
    }


    private PaymentAttemptResponse prepareResponse(BankPaymentResponse bankPaymentResponse, PaymentEntity paymentEntity) {
        return PaymentAttemptResponse.builder().paymentFailureReason(bankPaymentResponse.getPaymentFailureReason()).result(bankPaymentResponse.getResult()).id(paymentEntity.getId()).build();
    }

    private PaymentEntity prepareAndSavePaymentAttempt(BankPaymentResponse bankPaymentResponse, Payment payment) {
        PaymentEntity paymentEntity = modelMapper.map(payment, PaymentEntity.class);

        paymentEntity.setPaymentId(bankPaymentResponse.getPaymentId());
        paymentEntity.setStatus(bankPaymentResponse.getResult().name());

        return paymentRepository.save(paymentEntity);

    }

}