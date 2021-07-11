package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderItemPrice; // 상품가격은 달라질 수 있기 때문에 현재 주문상품 가격이 필요

    private int count;

    public void addItem(Item item) {
        this.item = item;
        item.getOrderItems().add(this);
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
