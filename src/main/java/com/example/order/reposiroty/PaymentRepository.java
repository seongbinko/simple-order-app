package com.example.order.reposiroty;

import com.example.order.entity.Member;
import com.example.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PaymentRepository extends JpaRepository<Payment,Long> {


}
