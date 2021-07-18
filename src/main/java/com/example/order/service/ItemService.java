package com.example.order.service;

import com.example.order.controller.dto.ItemResponseDto;
import com.example.order.controller.dto.ItemsRequestDto;
import com.example.order.entity.Category;
import com.example.order.entity.Item;
import com.example.order.reposiroty.CategoryRepository;
import com.example.order.reposiroty.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public List<ItemResponseDto> viewItems(ItemsRequestDto itemsRequestDto) {

        if(0 == itemsRequestDto.getCategoryId()) {
            List<Item> items = itemRepository.findByNameContainingOrderByName(itemsRequestDto.getKeyword());
            return createItemResponseDtoList(items);

        }
        Category category = categoryRepository.findById(Long.valueOf(itemsRequestDto.getCategoryId())).get();
        List<Item> items = itemRepository.findByCategoryOrderByName(category);
        return createItemResponseDtoList(items);
    }

    public List<ItemResponseDto> createItemResponseDtoList (List<Item> items) {

        return items.stream().map(
                item -> ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .imgUrl(item.getImgUrl())
                .price(item.getPrice())
                .stockQuantity(item.getStockQuantity())
                .status(item.getStatus())
                .build()).collect(Collectors.toList());
    }
}
