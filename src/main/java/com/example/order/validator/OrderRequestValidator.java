package com.example.order.validator;

import com.example.order.controller.dto.ItemsRequestDto;
import com.example.order.controller.dto.OrderRequestDto;
import com.example.order.reposiroty.CategoryRepository;
import com.example.order.reposiroty.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class OrderRequestValidator implements Validator {

    private final ItemRepository itemRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(OrderRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderRequestDto orderRequestDto = (OrderRequestDto) target;

        if(!itemRepository.existsById(orderRequestDto.getItemId())) {
            errors.rejectValue("itemId", "invalid.itemId","존재하지 않는 카테고리입니다.");
        }
    }
}
