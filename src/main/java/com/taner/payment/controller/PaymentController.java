package com.taner.payment.controller;

import com.taner.payment.model.Payment;
import com.taner.payment.model.PaymentAttemptResponse;
import com.taner.payment.model.PaymentDetailsResponse;
import com.taner.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentAttemptResponse> processPayment(@Valid @RequestBody Payment payment) {

        PaymentAttemptResponse paymentAttemptResponse = paymentService.processPayment(payment);

        return new ResponseEntity<PaymentAttemptResponse>(paymentAttemptResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetailsResponse> getCardDetails(@PathVariable Long id) {

        PaymentDetailsResponse payment = paymentService.getPayment(id);

        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}