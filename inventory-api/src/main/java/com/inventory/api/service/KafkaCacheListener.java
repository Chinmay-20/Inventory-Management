package com.inventory.api.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.cache.CacheManager;

@Service
public class KafkaCacheListener {
    
    private final CacheManager cacheManager;

    public KafkaCacheListener(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }

    @KafkaListener(topics = "product-events", groupId = "inventory-group")
    public void handleProductEvent(String message){

        System.out.println("Received Kafka message: " + message);

        if(message.startsWith("Created") || message.startsWith("Updated")){
            String productId = message.split(": ")[1];
            cacheManager.getCache("product").evict(productId);
        }
        else if(message.startsWith("Deleted")){
            cacheManager.getCache("products").clear();
        }
    }
}
