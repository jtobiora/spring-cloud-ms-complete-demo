package com.swiftfingers.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swiftfingers.orderservice.dto.TransactionRequest;
import com.swiftfingers.orderservice.dto.TransactionResponse;
import com.swiftfingers.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@RefreshScope
@Slf4j
public class OrderController {


    private final OrderService service;

    @Value("${author.name}")
    private String author;

    @PostMapping("/bookOrder")
    @CircuitBreaker(name = "orderService", fallbackMethod = "fallbackMethod")
    //@TimeLimiter(name = "orderService")
    //@Retry(name = "orderService")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) {
        try {
            return service.saveOrder(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/sender")
    public ResponseEntity getCodeAuthor () {
        return ResponseEntity.ok(author);
    }


    public TransactionResponse fallbackMethod(RuntimeException runtimeException) {
        log.info("Cannot Place Order Executing Fallback logic");
        TransactionResponse response = new TransactionResponse();
        response.setMessage("Service is currently down. Try again later");
        return response;
    }
}