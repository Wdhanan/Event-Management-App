package com.example.eventmanagementsystem.service;

import com.example.eventmanagementsystem.dto.ParticipantDto;
import com.example.eventmanagementsystem.dto.ParticipantMapper;
import com.example.eventmanagementsystem.entity.Event;
import com.example.eventmanagementsystem.entity.Participant;
import com.example.eventmanagementsystem.repository.EventRepository;
import com.example.eventmanagementsystem.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EventRepository eventRepository;

    // Récupérer tous les participants
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    // Créer un nouveau participant
    public Participant createParticipant(ParticipantDto participantDto) {
        Participant participant = ParticipantMapper.toEntity(participantDto);
        if (participantDto.getEvents() != null) {
            List<Event> events = participantDto.getEvents().stream()
                    .map(eventDto -> eventRepository.findById(eventDto.getId())
                            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventDto.getId())))
                    .collect(Collectors.toList());
            participant.setEvents(events);
        }
        return participantRepository.save(participant);
    }

    // Récupérer un participant par ID
    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Participant not found with id " + id));
    }

    // Mettre à jour un participant existant
    public Participant updateParticipant(Long id, ParticipantDto participantDto) {
        Participant participant = getParticipantById(id);

        participant.setName(participantDto.getName());
        participant.setEmail(participantDto.getEmail());
        participant.setParticipantRole(participantDto.getParticipantRole());
        participant.setPassword(participant.getPassword());
        if (participantDto.getEvents() != null) {
            List<Event> events = participantDto.getEvents().stream()
                    .map(eventDto -> eventRepository.findById(eventDto.getId())
                            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventDto.getId())))
                    .collect(Collectors.toList());
            participant.setEvents(events);

        }
        return participantRepository.save(participant);
    }

    // Supprimer un participant
    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }
}
