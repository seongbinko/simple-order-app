package com.example.order.controller;

import com.example.order.controller.dto.ItemsRequestDto;
import com.example.order.service.ItemService;
import com.example.order.validator.ItemRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/api/items")
    public ResponseEntity viewItems(@Valid ItemsRequestDto itemsRequestDto) {
       return ResponseEntity.ok().body(itemService.viewItems(itemsRequestDto));
    }
}
