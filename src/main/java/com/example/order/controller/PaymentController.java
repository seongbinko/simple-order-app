package com.example.order.controller;

import com.example.order.config.jwt.CustomUserDetails;
import com.example.order.controller.dto.PaymentResponseDto;
import com.example.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/api/payment")
    public ResponseEntity viewPayment(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(paymentService.viewPayment(userDetails));
    }

    @PostMapping("/api/payment")
    public ResponseEntity createPayment(@AuthenticationPrincipal CustomUserDetails userDetails) {
        paymentService.createPayment(userDetails);
        return ResponseEntity.noContent().build();
    }

}
