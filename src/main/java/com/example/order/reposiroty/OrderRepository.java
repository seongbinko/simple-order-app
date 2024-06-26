package com.example.order.reposiroty;

import com.example.order.entity.Member;
import com.example.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByMemberAndIsPaymentFalse(Member member);

    @Transactional
    void deleteByMemberAndIsPaymentFalse(Member member);
}
