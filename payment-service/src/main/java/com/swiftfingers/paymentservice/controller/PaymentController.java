package com.swiftfingers.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swiftfingers.paymentservice.model.Payment;
import com.swiftfingers.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) {
        return service.doPayment(payment);
    }

    @GetMapping("/{orderId}")
    public Payment findPaymentHistoryByOrderId(@PathVariable int orderId){
        try {
            return service.findPaymentHistoryByOrderId(orderId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}