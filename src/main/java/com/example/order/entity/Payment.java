package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder // builder를 사용할수 있게 합니다.
@Entity // jpa entity임을 알립니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "payments") // 'user' 테이블과 매핑됨을 명시
public class Payment extends BaseEntity{

    @OneToMany(mappedBy = "payment")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int totalPrice;

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setPayment(this);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
