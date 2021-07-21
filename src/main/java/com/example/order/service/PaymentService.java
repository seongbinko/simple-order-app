package com.example.order.service;

import com.example.order.config.jwt.CustomUserDetails;
import com.example.order.controller.dto.PaymentResponseDto;
import com.example.order.entity.Order;
import com.example.order.entity.Payment;
import com.example.order.reposiroty.OrderRepository;
import com.example.order.reposiroty.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public void createPayment(CustomUserDetails userDetails) {

        List<Order> orders = orderRepository.findAllByMemberAndIsPaymentFalse(userDetails.getMember());

        Payment payment = paymentRepository.save(Payment.builder()
                .member(userDetails.getMember())
                .build());

        for (Order order: orders) {
            order.setIsPayment(true);
            payment.addOrder(order);
            payment.plusTotalPrice(order.getOrderPrice());
        }
    }

    public PaymentResponseDto viewPayment(CustomUserDetails userDetails) {

        Optional<Payment> payment = paymentRepository.findFirstByMemberOrderByCreatedAtDesc(userDetails.getMember());

        if(!payment.isPresent()) {
            return null;
        }

        return PaymentResponseDto.builder()
                .totalPrice(payment.get().getTotalPrice())
                .createdAt(payment.get().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
    }
}
