package com.ecommerce.tracker.publisher.client;

import com.ecommerce.tracker.publisher.client.ConcurrencyMap;
import com.ecommerce.tracker.publisher.dao.EventDao;
import com.ecommerce.tracker.publisher.entities.Event;
import com.ecommerce.tracker.publisher.entities.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class Consumer extends ConcurrencyMap {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @JmsListener(destination = "local.inmemory.queue")
    public void onMessage(String msg){
        try {
            System.out.println("Event Received : " + msg);
            Event event = Event.fromJson(msg);
            if(!map.containsKey(event) || map.get(event)<4) {
                if (event != null){
                    try{
                    eventDao.save(new EventEntity(event));
                    map.put(event,4);
                    }catch (Exception e){
                        jmsTemplate.convertAndSend(queue,event);
                        if(map.containsKey(event))
                            map.put(event,map.get(event)+1);
                        else map.put(event,1);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
