After starting the application:
visit: http://localhost:8082/h2-console/
no password required
-------------------------------------------------------------------------------------------------------------------
Final Output Queries:
1. Percentage of users in each stage of the user journey: This indicates the proportion of users at each stage of the journey within the
application. It provides insights into how users progress through the different steps of the app.

SELECT
  event_type,
  COUNT(*) AS user_count,
  COUNT(*) * 100 / (SELECT COUNT(*) FROM event_entity) AS percentage
FROM
  event_entity
GROUP BY
  event_type;
  
2. Evaluation of the performance of different cities: This evaluation is based on the percentage of users from each city. It allows us to
assess how well or poorly different cities are performing in terms of user engagement with the application. By analyzing the distribution
of users across cities, we can identify which cities have a higher or lower percentage of users using the app.  
  
SELECT
  city,
  COUNT(*) AS user_count,
  COUNT(*) * 100 / (SELECT COUNT(*) FROM event_entity) AS percentage
FROM
  event_entity
GROUP BY
 city;

---------------------------------------------------------------------


Commands for local setup:
1. After cloning the repository, navigate to the saved directory
2. javac .\publisher\src\main\java\com\ecommerce\tracker\publisher\Main.java
3. java -jar .\publisher\target\publisher-0.0.1-SNAPSHOT.jar com.ecommerce.tracker.publisher.Main

User behavior assumptions:    
    User engagement assumption used for each step:
    Access:0.9,
    Banner:0.6,
    ListDisplayed:0.57,
    Selected:0.3,
    AddedToCart:0.2,
    Ordered:0.1
    
    At each step, number of users trickle down as per the above ratio

    In-memory-queue: Embedded ActiveMq
    DB: H2 in-memory DB
    
