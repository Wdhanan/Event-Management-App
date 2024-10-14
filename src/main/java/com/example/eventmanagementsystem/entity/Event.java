package com.example.eventmanagementsystem.entity;

import com.example.eventmanagementsystem.dto.EventDto;
import com.example.eventmanagementsystem.dto.ParticipantDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table( name ="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private int capacity;

    //@ManyToOne
    //private Participant participant;

    public EventDto getDto(){
        EventDto eventDto = new EventDto();
        eventDto.setId(id);
        eventDto.setName(name);
        eventDto.setCapacity(capacity);
        eventDto.setDescription(description);
        eventDto.setLocation(location);
        eventDto.setStartDate(startDate);
        eventDto.setEndDate(endDate);
        return eventDto;


    }
}
