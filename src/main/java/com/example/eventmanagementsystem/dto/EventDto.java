package com.example.eventmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EventDto {
    private Long id;
    private String name;
    private String description;
    private String location;

    private Date startDate;

    private Date endDate;
    private int capacity;

}
