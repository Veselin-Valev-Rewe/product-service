package com.example.product_service.service;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.message.Message;

public interface ProductMessageProducer {
    void sentMessage(String key, Message<ProductDto> Message);
}
