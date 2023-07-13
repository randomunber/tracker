package com.ecommerce.tracker.publisher.entities;

import java.util.Objects;

public abstract class EventAbstract {
    private int userId;
    private String eventType;

    private long timestamp;

    private String city;

    private int productId;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventAbstract that = (EventAbstract) o;

        if (userId != that.userId) return false;
        if (timestamp != that.timestamp) return false;
        return Objects.equals(eventType, that.eventType);
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (eventType != null ? eventType.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
