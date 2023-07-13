package com.ecommerce.tracker.publisher;

import com.ecommerce.tracker.publisher.entities.Event;
import com.ecommerce.tracker.publisher.enums.Cities;
import com.ecommerce.tracker.publisher.enums.Funnel;
import com.ecommerce.tracker.publisher.webhook.Webhook;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.DiscreteProbabilityCollectionSampler;
import org.apache.commons.rng.simple.RandomSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Driver extends Thread {
    private final int userId;

    private final String city;

    private final String url = "http://localhost:8082/webhook";

    private final Webhook webhook = new Webhook(url);

    public Driver(int userId) {
        this.userId = userId;
        this.city = String.valueOf(Cities.values()[new Random().nextInt(Cities.values().length)]);
    }

    @Override
    public void run() {
        System.out.println("User " + userId + " started");

        // Simulate user behavior and generate events
        List<Event> events = generateEvents();

        // Batch events and send them
        List<List<Event>> batches = batchEvents(events);
        for (List<Event> batch : batches) {
            sendEvents(batch);
        }

        System.out.println("User " + userId + " finished");
    }


    /*
    *
    * User engagement assumption used for each step:
    * Access:0.9,
    * Banner:0.6,
    * ListDisplayed:0.57,
    * Selected:0.3,
    * AddedToCart:0.2,
    * Ordered:0.1
    *
    * At each step, number of users trickle down as per the above ratio
    * */
    private List<Event> generateEvents() {

        UniformRandomProvider rng = RandomSource.MT.create();

        DiscreteProbabilityCollectionSampler<Funnel> sampler = new DiscreteProbabilityCollectionSampler(rng,
                new ArrayList<>(Arrays.asList(Funnel.values())),
                new double[]{0.9,0.6,0.57,0.3,0.2,0.1});

        Funnel funnelStep = sampler.sample();

        List<Event> events = new ArrayList<>();

        for(int i = 0; i<=funnelStep.ordinal(); i++){
            Random random = new Random();
            int product = random.nextInt(100);

            events.add(new Event(userId, Funnel.values()[i].toString(),System.currentTimeMillis(),city,product));
        }
        return events;
    }

    private List<List<Event>> batchEvents(List<Event> events) {
        int batchSize = 10;  // Number of events to include in each batch
        int numBatches = events.size() / batchSize;
        List<List<Event>> batches = new ArrayList<>();

        for (int i = 0; i < numBatches; i++) {
            List<Event> batch = events.subList(i * batchSize, (i + 1) * batchSize);
            batches.add(batch);
        }

        if (events.size() % batchSize != 0) {
            List<Event> lastBatch = events.subList(numBatches * batchSize, events.size());
            batches.add(lastBatch);
        }

        return batches;
    }

    private void sendEvents(List<Event> events) {
        for (Event event : events) {
            try {
                long startTime = System.nanoTime();
                webhook.sendNotification(event, url);
                long endTime = System.nanoTime();

                long durationInMillis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                System.out.println("Event sent for user " + userId);
                System.out.println("Response time: " + durationInMillis + " milliseconds");
            }catch(Exception e){
                e.printStackTrace();
            }
            finally {
                webhook.sendClear(event);
            }
        }
    }
}
