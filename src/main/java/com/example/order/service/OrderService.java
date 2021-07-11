package com.example.order.service;

import com.example.order.config.jwt.CustomUserDetails;
import com.example.order.controller.dto.OrderItemResponseDto;
import com.example.order.controller.dto.OrderRequestDto;
import com.example.order.controller.dto.OrderResponseDto;
import com.example.order.entity.Item;
import com.example.order.entity.Member;
import com.example.order.entity.Order;
import com.example.order.entity.OrderItem;
import com.example.order.exception.ResourceNotFoundException;
import com.example.order.reposiroty.ItemRepository;
import com.example.order.reposiroty.MemberRepository;
import com.example.order.reposiroty.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(OrderRequestDto orderRequestDto, CustomUserDetails userDetails) {


        Member member = memberRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "nickname", userDetails.getUsername()));


        Order newOrder = orderRepository.save(Order.builder()
                .isPayment(false).build());

        int orderPrice = 0;
        for(int i=0; i < orderRequestDto.getItemIdList().size(); i++) {
            Long itemId = orderRequestDto.getItemIdList().get(i);
            Integer count = orderRequestDto.getCountList().get(i);
            Integer price = orderRequestDto.getOrderItemPriceList().get(i);

            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

            OrderItem newOrderItem = OrderItem.builder()
                    .count(count)
                    .orderItemPrice(price).build();

            orderPrice += count * price;


            newOrderItem.addItem(item);
            newOrder.addOrderItem(newOrderItem);
        }
        newOrder.setOrderPrice(orderPrice);
        member.addOrder(newOrder);
    }

    @Transactional
    public void deleteOrder(CustomUserDetails userDetails) {
        Member member = memberRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "nickname", userDetails.getUsername()));
        orderRepository.deleteByMember(member);
    }

    public List<OrderResponseDto> viewOrders(CustomUserDetails userDetails) {
        Member member = memberRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "nickname", userDetails.getUsername()));
        List<Order> orders = orderRepository.findAllByMemberAndIsPaymentFalse(member);

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
