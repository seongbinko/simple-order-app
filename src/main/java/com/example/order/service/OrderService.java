package com.example.order.service;

import com.example.order.config.jwt.CustomUserDetails;
import com.example.order.controller.dto.OrderItemResponseDto;
import com.example.order.controller.dto.OrderRequestDto;
import com.example.order.controller.dto.OrderResponseDto;
import com.example.order.entity.Item;
import com.example.order.entity.Member;
import com.example.order.entity.Order;
import com.example.order.entity.OrderItem;
import com.example.order.reposiroty.ItemRepository;
import com.example.order.reposiroty.MemberRepository;
import com.example.order.reposiroty.OrderItemRepository;
import com.example.order.reposiroty.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    public void createOrder(List<OrderRequestDto> orderRequestDtos, CustomUserDetails userDetails) {

        Order newOrder = orderRepository.save(Order.builder()
                .isPayment(false)
                .member(userDetails.getMember())
                .build());

        int orderPrice = 0;
        for(OrderRequestDto orderRequestDto : orderRequestDtos) {

            Item item = itemRepository.findById(orderRequestDto.getItemId()).get();

            OrderItem newOrderItem = orderItemRepository.save(OrderItem.builder()
                    .orderItemPrice(orderRequestDto.getOrderItemPrice())
                    .count(orderRequestDto.getOrderItemCount())
                    .item(item)
                    .build());
            newOrder.addOrderItem(newOrderItem);
            orderPrice += newOrderItem.getOrderItemPrice() * newOrderItem.getCount();
        }
        newOrder.setOrderPrice(orderPrice);
    }

    public void deleteOrder(CustomUserDetails userDetails) {
        orderRepository.deleteByMemberAndIsPaymentFalse(userDetails.getMember());
    }

    public List<OrderResponseDto> viewOrders(CustomUserDetails userDetails) {

        List<Order> orders = orderRepository.findAllByMemberAndIsPaymentFalse(userDetails.getMember());

        return orders.stream().map(
                order -> OrderResponseDto.builder()
                        .id(order.getId())
                        .orderPrice(order.getOrderPrice())
                        .createdAt(order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .orderItemResponseDtos(
                                order.getOrderItems().stream().map(
                                        orderItem -> OrderItemResponseDto.builder()
                                                .name(orderItem.getItem().getName())
                                                .count(orderItem.getCount())
                                                .orderItemPrice(orderItem.getOrderItemPrice())
                                                .build()
                                ).collect(Collectors.toList())
                        ).build()
        ).collect(Collectors.toList());
    }
}
