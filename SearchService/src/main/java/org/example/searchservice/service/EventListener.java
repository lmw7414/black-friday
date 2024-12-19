package org.example.searchservice.service;

import blackfriday.protobuf.EdaMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventListener {

    private final SearchService searchService;

    @KafkaListener(topics = "product_tags_added")
    public void consumeTagAdded(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.ProductTags.parseFrom(message);
        log.info("[product_tags_added] consumed: {}", object);

        searchService.addTagCache(object.getProductId(), object.getTagsList());
    }

    @KafkaListener(topics = "product_tags_removed")
    public void consumeTagRemoved(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.ProductTags.parseFrom(message);
        log.info("[product_tags_removed] consumed: {}", object);

        searchService.removeTagCache(object.getProductId(), object.getTagsList());
    }
}
