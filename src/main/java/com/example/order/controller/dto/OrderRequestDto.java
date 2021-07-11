package com.example.order.controller.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class OrderRequestDto {

    private ArrayList<Long> itemIdList;

    private ArrayList<Integer> orderItemPriceList;

    private ArrayList<Integer> countList;
}
