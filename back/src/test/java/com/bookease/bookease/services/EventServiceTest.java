package com.bookease.bookease.services;

import com.bookease.bookease.dtos.mappers.EventMapper;
import com.bookease.bookease.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {


    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setup(){
        //Setup
    }

    @Test
    void getEventById() {
    }

    @Test
    void getAllEvents() {
    }

    @Test
    void createEvent() {
    }

    @Test
    void saveEvent() {
    }

    @Test
    void deleteEvent() {
    }
}