package com.example.order.service;

import com.example.order.config.jwt.CustomUserDetails;
import com.example.order.controller.dto.PaymentResponseDto;
import com.example.order.entity.Member;
import com.example.order.entity.Order;
import com.example.order.entity.Payment;
import com.example.order.reposiroty.MemberRepository;
import com.example.order.reposiroty.OrderRepository;
import com.example.order.reposiroty.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @PersistenceContext
    EntityManager em;

    public void createPayment(CustomUserDetails userDetails) {

        Member member = memberRepository.findByNickname(userDetails.getUsername()).get();

        List<Order> orders = orderRepository.findAllByMemberAndIsPaymentFalse(userDetails.getMember());
        int totalPrice = orders.stream()
                .mapToInt(Order::getOrderPrice)
                .sum();

        Payment payment = paymentRepository.save(Payment.builder()
                .totalPrice(totalPrice)
                .build());

        for (Order order: orders) {
            order.setIsPayment(true);
            payment.addOrder(order);
        }
        member.addPayment(payment);
    }

    public ResponseEntity viewPayment(CustomUserDetails userDetails) {
        Member member = memberRepository.findByNickname(userDetails.getUsername()).get();

        if(member.getPayments().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        TypedQuery<Payment> query = em.createQuery("select p from Payment p where p.member = :member order by p.createdAt desc ", Payment.class);
        query.setParameter("member",member);
        query.setMaxResults(1);

        Payment payment = query.getSingleResult();
        return ResponseEntity.ok().body(PaymentResponseDto.builder()
                .totalPrice(payment.getTotalPrice())
                .createdAt(payment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build());
    }
}
