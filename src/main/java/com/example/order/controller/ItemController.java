package com.example.order.controller;

import com.example.order.controller.dto.ItemsRequestDto;
import com.example.order.service.ItemService;
import com.example.order.validator.ItemRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemRequestValidator itemRequestValidator;

    @InitBinder("itemsRequestDto")
    public void itemsBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(itemRequestValidator);
    }
    /**
     * 상품 전체 조회
     */
    @GetMapping("/api/items")
    public ResponseEntity viewItems(@Valid ItemsRequestDto itemsRequestDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
       return ResponseEntity.ok().body(itemService.viewItems(itemsRequestDto));
    }
}
