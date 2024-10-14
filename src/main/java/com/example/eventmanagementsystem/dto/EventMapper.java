package com.example.eventmanagementsystem.dto;

import com.example.eventmanagementsystem.entity.Event;

public class EventMapper {

    public static EventDto toDto(Event event) {
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setStartDate(event.getStartDate());
        dto.setEndDate(event.getEndDate());
        dto.setCapacity(event.getCapacity());
        return dto;
    }

    public static Event toEntity(EventDto dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setCapacity(dto.getCapacity());
        return event;
    }
}
