package com.example.order.reposiroty;

import com.example.order.entity.Item;
import com.example.order.entity.Order;
import com.example.order.entity.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired OrderRepository orderRepository;
    @Autowired OrderItemRepository orderItemRepository;
    @Autowired ItemRepository itemRepository;

    @PersistenceContext EntityManager em;

    @Test
    @DisplayName("주문정보 및 주문 아이템을 페치 조인 테스트")
    void findAllByMemberAndIsPaymentFalse() {

        OrderItem orderItemA = orderItemRepository.save(
                OrderItem.builder()
                .orderItemPrice(10000)
                .count(3)
                .build()
        );

        OrderItem orderItemB = orderItemRepository.save(
                OrderItem.builder()
                .orderItemPrice(20000)
                .count(1)
                .build()
        );
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItemA);
        orderItemList.add(orderItemB);

        Order order = orderRepository.save(
                Order.builder()
                        .orderPrice(50000)
                        .isPayment(false)
                        .build()
        );

        order.addOrderItem(orderItemA);
        order.addOrderItem(orderItemB);

        em.flush();
        em.clear();

        List<Order> orders = orderRepository.findAll();

        for(Order o : orders) {
            for(OrderItem orderItem : o.getOrderItems()) {
                System.out.println("orderItem.getCount() = " + orderItem.getClass());
            }
        }
    }
}