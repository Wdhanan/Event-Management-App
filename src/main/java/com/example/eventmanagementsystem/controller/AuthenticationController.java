package com.example.eventmanagementsystem.controller;


import com.example.eventmanagementsystem.dto.AuthenticationRequest;
import com.example.eventmanagementsystem.dto.ParticipantDto;
import com.example.eventmanagementsystem.dto.SignupRequestDTO;
import com.example.eventmanagementsystem.entity.Participant;
import com.example.eventmanagementsystem.jwt.UserDetailsServiceImpl;
import com.example.eventmanagementsystem.repository.ParticipantRepository;
import com.example.eventmanagementsystem.service.AuthService;
import com.example.eventmanagementsystem.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // to create the JWTToken

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ParticipantRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;// handle the login or logout logic

    public static final String TOKEN_PREFIX ="Bearer ";

    public static final  String HEADER_STRING = "Authorization";

    @PostMapping("/admin/sign-up")
    public ResponseEntity<?> signupAdmin(@RequestBody SignupRequestDTO signupRequestDTO){

        // if the user wants to register with a mail which already exits
        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Admin already exists with this Email!", HttpStatus.NOT_ACCEPTABLE);
        }

        ParticipantDto createadUser = authService.signupAdmin(signupRequestDTO);
        return  new ResponseEntity<>(createadUser, HttpStatus.OK);


    }

    @PostMapping("/normal-participant/sign-up")
    public ResponseEntity<?> signupNormalParticipant(@RequestBody SignupRequestDTO signupRequestDTO){

        // if the user wants to register with a mail which already exits
        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Company already exists with this Email!", HttpStatus.NOT_ACCEPTABLE);
        }

        ParticipantDto createadUser = authService.signupNormalParticipant(signupRequestDTO);
        return  new ResponseEntity<>(createadUser, HttpStatus.OK);


    }


    @CrossOrigin("http://localhost:4200/")
    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        Participant user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

        // Verwenden von ObjectMapper anstelle von JSONObject
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(Map.of(
                "userId", user.getId(),
                "role", user.getParticipantRole()
        ));

        response.getWriter().write(jsonResponse);

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization,X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }


}
