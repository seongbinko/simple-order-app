package com.example.order.validator;

import com.example.order.controller.dto.ItemsRequestDto;
import com.example.order.reposiroty.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ItemRequestValidator implements Validator {

    private final CategoryRepository categoryRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ItemsRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ItemsRequestDto itemsRequestDto = (ItemsRequestDto) target;

        if(!(0 == itemsRequestDto.getCategoryId() || categoryRepository.existsById(Long.valueOf(itemsRequestDto.getCategoryId())))){
            errors.rejectValue("categoryId", "invalid","존재하지 않은 카테고리입니다.");
        }
    }
}
