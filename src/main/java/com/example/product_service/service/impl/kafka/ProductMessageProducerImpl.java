package com.example.product_service.service.impl.kafka;

import com.example.product_service.data.entity.ProducedMessage;
import com.example.product_service.data.enums.MessageStatus;
import com.example.product_service.data.repository.ProducedMessageRepository;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.message.Message;
import com.example.product_service.service.MessageValidatorService;
import com.example.product_service.service.ProductMessageProducer;
import com.example.product_service.util.kafka.KafkaTopics;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductMessageProducerImpl implements ProductMessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProducedMessageRepository producedMessageRepository;
    private final ObjectMapper objectMapper;
    private final MessageValidatorService messageValidatorService;

    @Override
    public void sentMessage(String key, Message<ProductDto> message) {
        log.info("Sending message with key {} to topic {}", key, KafkaTopics.PRODUCTS_TOPIC);
        try {
            var payload = objectMapper.writeValueAsString(message);
            messageValidatorService.validate(KafkaTopics.PRODUCTS_TOPIC, payload);
            kafkaTemplate.send(KafkaTopics.PRODUCTS_TOPIC, key, payload).get();
            saveProducedMessage(key, message, MessageStatus.SENT);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Kafka send interrupted", e);
            saveProducedMessage(key, message, MessageStatus.FAILED);
        } catch (Exception e) {
            log.error("Kafka send failed", e);
            saveProducedMessage(key, message, MessageStatus.FAILED);
        }
    }

    private void saveProducedMessage(String key, Message<ProductDto> message, MessageStatus status) {
        var producedMessage = ProducedMessage.builder()
                .msgKey(key)
                .payload(message.toString())
                .status(status)
                .build();

        producedMessageRepository.save(producedMessage);
    }
}
