package com.ecommerce.tracker.publisher.webhook;

import com.ecommerce.tracker.publisher.entities.Event;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

@Component
public class Webhook {

    public Webhook(String url) {
        //this.url = url;
        this.clearUrl = url + "/clear";
    }

    public Webhook(){}

    //private String url;

    private String clearUrl;

    public void sendClear(Event event){
        sendNotification(event,clearUrl);
    }
    public void sendNotification(Event event,String url) {
        try {
            int retryLimit = 3;
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);

            // Set the request body with the event data
            StringEntity params = new StringEntity(event.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);

            // Send the HTTP POST request
            HttpResponse response = httpClient.execute(request);

            // Process the response if needed
            int statusCode = response.getStatusLine().getStatusCode();
            int retry = 0;
            while(retry < retryLimit && statusCode!=200){
                response = httpClient.execute(request);
                statusCode = response.getStatusLine().getStatusCode();
                retry++;
            }
            System.out.println("Webhook response status code: " + statusCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
