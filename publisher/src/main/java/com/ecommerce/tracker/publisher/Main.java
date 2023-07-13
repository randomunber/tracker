package com.ecommerce.tracker.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        //Start Driver Threads
        int numUsers = 21;
        List<Driver> drivers = new ArrayList<>();

        // Create and start multiple user threads
        for (int i = 1; i < numUsers; i++) {
            Driver driver = new Driver(i);
            drivers.add(driver);
            driver.start();
        }

        // Wait for all user threads to finish
        for (Driver driver : drivers) {
            try {
                driver.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Final Output

    }
}
