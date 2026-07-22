package com.store.conveniencestore.dto;

public record SaleItemCreateRequest (
        Integer productId,
        Integer quantity
){
}
