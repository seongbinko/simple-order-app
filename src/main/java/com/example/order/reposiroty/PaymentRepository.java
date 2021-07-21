package com.example.order.reposiroty;

import com.example.order.entity.Member;
import com.example.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findFirstByMemberOrderByCreatedAtDesc(Member member);
}
