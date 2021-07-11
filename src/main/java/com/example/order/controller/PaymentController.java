package com.example.order.controller;

import com.example.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/payment")
    public ResponseEntity createPayment(@AuthenticationPrincipal UserDetails userDetails) {
        paymentService.createPayment(userDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/payment")
    public ResponseEntity viewPayment(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(paymentService.viewPayment(userDetails));
    }
}
