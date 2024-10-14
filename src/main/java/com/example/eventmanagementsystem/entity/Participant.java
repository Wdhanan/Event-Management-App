package com.example.eventmanagementsystem.entity;

import com.example.eventmanagementsystem.dto.EventDto;
import com.example.eventmanagementsystem.dto.ParticipantDto;
import com.example.eventmanagementsystem.dto.ParticipantMapper;
import com.example.eventmanagementsystem.enums.ParticipantRole;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table( name ="participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    //@OneToMany (mappedBy = "participant")
    @ManyToMany
    private List<Event> events; // Participant attends multiple events

    private String password;
    private ParticipantRole participantRole; // role of the participants

    public ParticipantDto getDto(){
        ParticipantDto userDto = new ParticipantDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setEmail(email);

        userDto.setPassword(password);

        if (events != null) {
            userDto.setEvents(events.stream()
                    .map(Event::getDto)
                    .collect(Collectors.toList()));
        }


        userDto.setParticipantRole(participantRole);


        return userDto;

    }


    // Getters et Setters
}
