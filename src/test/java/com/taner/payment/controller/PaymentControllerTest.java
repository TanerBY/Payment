package com.taner.payment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.taner.payment.model.PaymentDetailsResponse;
import com.taner.payment.model.PaymentResult;
import com.taner.payment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void shouldCreateCard() throws Exception {

        this.mockMvc.perform(post("/api/payment").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"amount\": 55,\n" +
                        "  \"cardHolder\": \"string\",\n" +
                        "  \"cardNumber\": \"1234123412341234\",\n" +
                        "  \"currency\": \"GBP\",\n" +
                        "  \"cvv\": \"123\",\n" +
                        "  \"expirationDate\": \"11/03\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetValidationErrorForCardNumber() throws Exception {

        this.mockMvc.perform(post("/api/payment").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"amount\": 55,\n" +
                        "  \"cardHolder\": \"string\",\n" +
                        "  \"cardNumber\": \"1234123441234\",\n" +
                        "  \"currency\": \"GBP\",\n" +
                        "  \"cvv\": \"123\",\n" +
                        "  \"expirationDate\": \"11/03\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnCard() throws Exception {
        PaymentDetailsResponse card = PaymentDetailsResponse.builder().id(1).cardHolder("Taner").cardNumber("1234123412341234").cvv("123").expirationDate("10/10").amount(BigDecimal.TEN).status(PaymentResult.SUCCESS.name()).currency("GBP").build();
        when(paymentService.getPayment(1L)).thenReturn(card);
        this.mockMvc.perform(get("/api/payment/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("cardHolder").value("Taner"))
                .andExpect(jsonPath("cardNumber").value("1234123412341234"))
                .andExpect(jsonPath("cvv").value("123"))
                .andExpect(jsonPath("expirationDate").value("10/10"))
                .andExpect(jsonPath("amount").value("10"))
                .andExpect(jsonPath("status").value("SUCCESS"))
                .andExpect(jsonPath("currency").value("GBP"));
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        when(paymentService.getPayment(1L)).thenThrow(new ResponseStatusException(NOT_FOUND, "Payment not found"));
        this.mockMvc.perform(get("/api/payment/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


}