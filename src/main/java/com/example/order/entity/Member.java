package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class Member extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setMember(this);
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setMember(this);
    }

}
