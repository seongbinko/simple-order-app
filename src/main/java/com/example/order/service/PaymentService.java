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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @PersistenceContext
    EntityManager em;

    public void createPayment(CustomUserDetails userDetails) {

        List<Order> orders = orderRepository.findAllByMemberAndIsPaymentFalse(userDetails.getMember());
        int totalPrice = orders.stream()
                .mapToInt(Order::getOrderPrice)
                .sum();

        Payment payment = paymentRepository.save(Payment.builder()
                .totalPrice(totalPrice)
                .member(userDetails.getMember())
                .build());

        for (Order order: orders) {
            order.setIsPayment(true);
            payment.addOrder(order);
        }
    }

    public PaymentResponseDto viewPayment(CustomUserDetails userDetails) {

        if(!paymentRepository.existsByMember(userDetails.getMember())) {
            return null;
        }

        TypedQuery<Payment> query = em.createQuery("select p from Payment p where p.member = :member order by p.createdAt desc ", Payment.class);
        query.setParameter("member",userDetails.getMember());
        query.setMaxResults(1);
        Payment payment = query.getSingleResult();

        return PaymentResponseDto.builder()
                .totalPrice(payment.getTotalPrice())
                .createdAt(payment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
    }
}
