package com.springproj.journalApp.controller;

import com.springproj.journalApp.dto.LoginRequest;
import com.springproj.journalApp.dto.SignupRequest;
import com.springproj.journalApp.service.userdetailserviceimpl;
import com.springproj.journalApp.service.userservice;
import com.springproj.journalApp.utils.jwtutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
//isme hum unauthenticated service denge





@RestController
@RequestMapping("/public")
@Slf4j
public class publiccontroller {
    @Autowired
    private userservice userservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
private userdetailserviceimpl  userdetail;

    @Autowired
    private jwtutil jwtutil;


    @GetMapping("/health-check")
    public String healthchek(){
        return "ok";

    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        // Validation is enforced by @Valid; duplicate usernames surface as 409 via the
        // GlobalExceptionHandler. No more opaque boolean / blanket 400.
        userservice.savenewuser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        // Bad credentials propagate to the handler as 401; we no longer mask every
        // failure (including server errors) as a generic 400.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = userdetail.loadUserByUsername(request.getUsername());
        String jwt = jwtutil.generateToken(userDetails.getUsername());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
