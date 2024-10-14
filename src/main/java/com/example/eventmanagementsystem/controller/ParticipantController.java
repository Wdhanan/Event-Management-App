package com.example.eventmanagementsystem.controller;

import com.example.eventmanagementsystem.dto.ParticipantDto;
import com.example.eventmanagementsystem.entity.Participant;
import com.example.eventmanagementsystem.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    // Récupérer tous les participants
    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    // Créer un nouveau participant
    @PostMapping
    public ResponseEntity<Participant> createParticipant(@RequestBody ParticipantDto participantDto) {
        Participant createdParticipant = participantService.createParticipant(participantDto);
        return new ResponseEntity<>(createdParticipant, HttpStatus.CREATED);
    }

    // Récupérer un participant par ID
    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        Participant participant = participantService.getParticipantById(id);
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

    // Mettre à jour un participant
    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(
            @PathVariable Long id,
            @RequestBody ParticipantDto participantDto) {
        Participant updatedParticipant = participantService.updateParticipant(id, participantDto);
        return new ResponseEntity<>(updatedParticipant, HttpStatus.OK);
    }

    // Supprimer un participant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
