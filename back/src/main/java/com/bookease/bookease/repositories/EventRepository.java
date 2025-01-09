package com.bookease.bookease.repositories;
import com.bookease.bookease.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository <Event, UUID> {


}
