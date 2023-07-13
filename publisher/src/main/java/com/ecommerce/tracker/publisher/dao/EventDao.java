package com.ecommerce.tracker.publisher.dao;

import com.ecommerce.tracker.publisher.entities.Event;
import com.ecommerce.tracker.publisher.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDao extends JpaRepository<EventEntity, Long> {

}
