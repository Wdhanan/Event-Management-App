package com.example.eventmanagementsystem.controller;

import com.example.eventmanagementsystem.dto.EventDto;
import com.example.eventmanagementsystem.entity.Event;
import com.example.eventmanagementsystem.entity.Participant;
import com.example.eventmanagementsystem.service.NotificationService;
import com.example.eventmanagementsystem.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.eventmanagementsystem.service.EventService;

import java.util.List;

@RequestMapping("/api/events")
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    ParticipantService participantService;
    public void sendEventReminder(EventDto eventDto, long participantId) {
        Participant participant = participantService.getParticipantById(participantId);
        String subject = "Reminder: Your event is coming up!";
        String body = "Dear " + participant.getName() + ",\n" +
                "Don't forget about the upcoming event: " + eventDto.getName() +
                " on " + eventDto.getStartDate() + ".\n\nSee you there!";
        notificationService.sendReminderEmail(participant.getEmail(), subject, body);
    }

    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(event -> ResponseEntity.ok().body(event))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{participantId}")
    public EventDto createEvent(@RequestBody EventDto eventDto, @PathVariable("participantId") Long participantId) {
        EventDto eventDto1 = eventService.createEvent(eventDto, participantId);
        sendEventReminder(eventDto, participantId);

        return eventDto1;
    }

    @PutMapping("/{id}/{participantId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDetails, @PathVariable("participantId") Long participantId) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDetails, participantId));
    }

    @DeleteMapping("/{id}/{participantId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, @PathVariable("participantId") Long participantId) {
        eventService.deleteEvent(id, participantId);
        return ResponseEntity.noContent().build();
    }
}
