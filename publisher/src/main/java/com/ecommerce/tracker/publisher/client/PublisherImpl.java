package com.ecommerce.tracker.publisher.client;

import com.ecommerce.tracker.publisher.client.ConcurrencyMap;
import com.ecommerce.tracker.publisher.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;

@RestController
@RequestMapping("/webhook")
public class PublisherImpl extends ConcurrencyMap {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    /*
    *
    * To avoid setting up a new queue
    * to remove entry from the map, this method accesses the map,
    * however, it's not recommended, and another controller on the client end should handle this
    *
    * */
    @PostMapping("/clear")
    public String publish(@RequestBody Event event){

        map.remove(event);
        return "Success";
    }

    @PostMapping()
    public ResponseEntity<String> handleWebhook(@RequestBody Event event){
        jmsTemplate.convertAndSend(queue, event.toString());
        return ResponseEntity.status(HttpStatus.OK).body("Webhook Received");
    }
}
