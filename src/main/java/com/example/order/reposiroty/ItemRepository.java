package com.example.order.reposiroty;

import com.example.order.entity.Category;
import com.example.order.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByNameContainingOrderByName(String keyword);

    List<Item> findByCategoryOrderByName(Category category);
}
