package com.swiftfingers.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftfingers.paymentservice.model.Payment;
import com.swiftfingers.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {


    private final PaymentRepository repository;

    public Payment doPayment(Payment payment)  {
        try {
            payment.setPaymentStatus(paymentProcessing());
            payment.setTransactionId(UUID.randomUUID().toString());
            log.info("Payment-Service Request : {}",new ObjectMapper().writeValueAsString(payment));

        } catch (JsonProcessingException e) {
            log.error("Exception trace -> {}", e);
        }

        return repository.save(payment);
    }


    public String paymentProcessing(){
        //api should be 3rd party payment gateway (paypal,paytm...)
        return new Random().nextBoolean()?"success":"false";
    }


    public Payment findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
        Payment payment=repository.findByOrderId(orderId);
        log.info("paymentService findPaymentHistoryByOrderId : {}",new ObjectMapper().writeValueAsString(payment));
        return payment ;
    }
}