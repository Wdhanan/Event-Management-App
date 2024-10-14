package com.example.eventmanagementsystem.service;



import com.example.eventmanagementsystem.dto.EventDto;
import com.example.eventmanagementsystem.dto.EventMapper;
import com.example.eventmanagementsystem.entity.Event;
import com.example.eventmanagementsystem.entity.Participant;
import com.example.eventmanagementsystem.enums.ParticipantRole;
import com.example.eventmanagementsystem.repository.EventRepository;
import com.example.eventmanagementsystem.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    // Check if the participant is an admin before performing create, update, or delete
    private void checkAdminPrivileges(Long participantId) {
        Optional<Participant> participant = participantRepository.findById(participantId);

        if(participant.isPresent()){
            if (participant.get().getParticipantRole() != ParticipantRole.ADMIN) {
                throw new RuntimeException("Permission Denied: Only ADMINs can perform this action.");
            }

        }


    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(EventMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<EventDto> getEventById(Long id) {
        return eventRepository.findById(id)
                .map(EventMapper::toDto);
    }

    public EventDto createEvent(EventDto eventDto, Long participantId) {
        checkAdminPrivileges(participantId); // Check if participant is admin
        Event event = EventMapper.toEntity(eventDto);
        Event savedEvent = eventRepository.save(event);
        return EventMapper.toDto(savedEvent);
    }

    public EventDto updateEvent(Long id, EventDto eventDetails, Long participantId) {
        checkAdminPrivileges(participantId); // Check if participant is admin
        Event event = eventRepository.findById(id).orElseThrow();
        event.setName(eventDetails.getName());
        event.setDescription(eventDetails.getDescription());
        event.setLocation(eventDetails.getLocation());
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setCapacity(eventDetails.getCapacity());
        Event updatedEvent = eventRepository.save(event);
        return EventMapper.toDto(updatedEvent);
    }

    public void deleteEvent(Long id, Long participantId) {
        checkAdminPrivileges(participantId); // Check if participant is admin
        eventRepository.deleteById(id);
    }
}
