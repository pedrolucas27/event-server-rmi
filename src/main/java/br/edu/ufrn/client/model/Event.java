package br.edu.ufrn.client.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Event implements Serializable {

    private String description;
    private ZonedDateTime date;
    private EventType eventType;

    public Event(String description, ZonedDateTime date, EventType eventType) {
        this.description = description;
        this.date = date;
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public EventType getEventType() {
        return eventType;
    }
}
