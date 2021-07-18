package com.example.order.controller;

import com.example.order.config.jwt.CustomUserDetails;
import com.example.order.controller.dto.OrderRequestDto;
import com.example.order.service.OrderService;
import com.example.order.validator.OrderRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderRequestValidator orderRequestValidator;

    @InitBinder("orderRequestDtos")
    public void createOrderBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(orderRequestValidator);
    }

    @GetMapping("/api/orders")
    public ResponseEntity viewOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(orderService.viewOrders(userDetails));
    }

    @PostMapping("/api/orders")
    public ResponseEntity createOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestBody @Valid List<OrderRequestDto> orderRequestDtos) {
        orderService.createOrder(orderRequestDtos,userDetails);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/orders")
    public ResponseEntity deleteOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.deleteOrder(userDetails);
        return ResponseEntity.noContent().build();
    }
}
