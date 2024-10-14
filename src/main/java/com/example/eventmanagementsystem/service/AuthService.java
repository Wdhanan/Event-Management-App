package com.example.eventmanagementsystem.service;


import com.example.eventmanagementsystem.dto.ParticipantDto;
import com.example.eventmanagementsystem.dto.SignupRequestDTO;
import com.example.eventmanagementsystem.entity.Event;
import com.example.eventmanagementsystem.entity.Participant;
import com.example.eventmanagementsystem.enums.ParticipantRole;
import com.example.eventmanagementsystem.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthService  {

    @Autowired
    private ParticipantRepository participantRepository; // injection of the UserRepository

    public ParticipantDto signupAdmin(SignupRequestDTO signupRequestDTO){

        Participant user = new Participant();
        user.setName(signupRequestDTO.getName());

        user.setEmail(signupRequestDTO.getEmail());
        user.setEvents(signupRequestDTO.getEvents());

        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword())); // encrypt the Pass
        user.setParticipantRole(ParticipantRole.ADMIN); // ADMIN

        return participantRepository.save(user).getDto(); // save the user into the Database and return the dto from it

    }

    public Boolean presentByEmail (String email){

        return  participantRepository.findFirstByEmail(email) !=null; // return true or false
    }

    public ParticipantDto signupNormalParticipant(SignupRequestDTO signupRequestDTO){

        Participant user = new Participant();
        user.setName(signupRequestDTO.getName());

        user.setEmail(signupRequestDTO.getEmail());
        user.setEvents(signupRequestDTO.getEvents());

        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword())); // encrypt the Pass
        user.setParticipantRole(ParticipantRole.NORMAL_PARTICIPANT); // ADMIN

        return participantRepository.save(user).getDto(); // save the user into the Database and return the dto from it

    }
}
