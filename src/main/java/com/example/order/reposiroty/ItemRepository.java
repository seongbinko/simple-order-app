package com.example.order.reposiroty;

import com.example.order.entity.Category;
import com.example.order.entity.Item;
import com.example.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {


    List<Item> findByCategoryAndNameStartingWithOrderByName(Category category, String keyword);

    List<Item> findByNameStartsWithOrderByNameAsc(String keyword);
}
