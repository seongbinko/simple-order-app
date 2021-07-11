package com.example.order.controller;

import com.example.order.config.jwt.CustomUserDetails;
import com.example.order.controller.dto.OrderRequestDto;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity createOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @Valid OrderRequestDto orderRequestDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        orderService.createOrder(orderRequestDto,userDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/orders")
    public ResponseEntity viewOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(orderService.viewOrders(userDetails));
    }

    @DeleteMapping("/api/orders")
    public ResponseEntity deleteOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.deleteOrder(userDetails);
        return ResponseEntity.ok().build();
    }
}
