package com.example.eventmanagementsystem.controller;

import com.example.eventmanagementsystem.entity.Event;
import com.example.eventmanagementsystem.entity.Participant;
import com.example.eventmanagementsystem.service.NotificationService;
import com.example.eventmanagementsystem.service.ParticipantService;
import com.example.eventmanagementsystem.service.QRCodeService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    ParticipantService participantService;

    @GetMapping("/generate/{participantId}")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long participantId) {
        try {
            Participant participant = participantService.getParticipantById(participantId);
            String qrText = "Participant ID: " + participantId +" Has Access to  the Events:" +participant.getEvents(); // Exemple de texte pour le QR Code
            byte[] qrImage = qrCodeService.generateQRCode(qrText, 350, 350);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "image/png");

            return new ResponseEntity<>(qrImage, headers, HttpStatus.OK);

        } catch (WriterException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
