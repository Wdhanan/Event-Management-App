package com.example.eventmanagementsystem.dto;


import com.example.eventmanagementsystem.entity.Participant;

import java.util.stream.Collectors;

public class ParticipantMapper {

    public static ParticipantDto toDto(Participant participant) {
        ParticipantDto dto = new ParticipantDto();
        dto.setId(participant.getId());
        dto.setName(participant.getName());
        dto.setEmail(participant.getEmail());
        dto.setPassword(participant.getPassword());
        dto.setParticipantRole(participant.getParticipantRole());
        dto.setEvents(participant.getEvents().stream().map(EventMapper::toDto).collect(Collectors.toList()));
        return dto;
    }

    public static Participant toEntity(ParticipantDto dto) {
        Participant participant = new Participant();
        participant.setId(dto.getId());
        participant.setName(dto.getName());
        participant.setEmail(dto.getEmail());
        participant.setParticipantRole(dto.getParticipantRole());
        participant.setPassword(dto.getPassword());

        participant.setEvents(dto.getEvents().stream().map(EventMapper::toEntity).collect(Collectors.toList()));

        return participant;
    }
}

