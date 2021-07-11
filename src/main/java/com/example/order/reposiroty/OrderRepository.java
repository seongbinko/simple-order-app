package com.example.order.reposiroty;

import com.example.order.entity.Member;
import com.example.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Transactional
    void deleteByMember(Member member);

    List<Order> findAllByMember(Member member);
}
