package com.example.product_service.message;

import com.example.product_service.message.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {
    private ActionType actionType;

    private T payload;
}
