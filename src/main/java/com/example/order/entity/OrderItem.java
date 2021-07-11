package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder // builder를 사용할수 있게 합니다.
@Entity // jpa entity임을 알립니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "order_items") // 'user' 테이블과 매핑됨을 명시
public class OrderItem extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderItemPrice; // 상품가격은 달라질 수 있기 때문에 현재 주문상품가격이 필요

    private int count;


    public void addItem(Item item) {
        if(this.item != null) {
            this.item.getOrderItems().remove(this);
        }
        this.item = item;
        item.getOrderItems().add(this);
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
