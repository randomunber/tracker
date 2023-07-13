package com.ecommerce.tracker.publisher.client;

import com.ecommerce.tracker.publisher.entities.Event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ConcurrencyMap {
    Map<Event, Integer> map = new ConcurrentHashMap<Event,Integer>();
}
