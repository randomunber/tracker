package com.ecommerce.tracker.publisher.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Event extends EventAbstract{

    public Event(){}

    public Event(int userId, String eventType, long timestamp,String city, int productId) {
        this.setUserId(userId);
        this.setEventType(eventType);
        this.setTimestamp(timestamp);
        this.setCity(city);
        this.setProductId(productId);
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // or handle the exception as needed
        }
    }

    public static Event fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, Event.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // or handle the exception as needed
        }
    }
}
