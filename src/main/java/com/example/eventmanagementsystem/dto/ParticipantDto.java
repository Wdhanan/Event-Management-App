package com.example.eventmanagementsystem.dto;

import com.example.eventmanagementsystem.entity.Event;
import com.example.eventmanagementsystem.enums.ParticipantRole;
import lombok.Data;

import java.util.List;
@Data
public class ParticipantDto {
    private Long id;
    private String name;
    private String email;
    private String password; // Si nécessaire pour la création/mise à jour
    private ParticipantRole participantRole;
    private List<EventDto> events; // Liste des événements

    // Getters et Setters
}

