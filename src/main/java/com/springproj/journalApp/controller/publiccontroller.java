package com.springproj.journalApp.controller;

import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.service.userdetailserviceimpl;
import com.springproj.journalApp.service.userservice;
import com.springproj.journalApp.utils.jwtutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?>  signup(@RequestBody user userbody){
        boolean created = userservice.savenewuser(userbody);
        if(created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not create user");
        }
    }


    @PostMapping("/login")

    public ResponseEntity<String>  login(@RequestBody user user){
try{
authenticationManager.authenticate
        (new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
    UserDetails userDetails = userdetail.loadUserByUsername(user.getUsername());
    String jwt = jwtutil.generateToken(userDetails.getUsername());
    return new ResponseEntity<>(jwt, HttpStatus.OK);
}
catch(Exception e){
    log.error("exceptiom occurred while createauthtoken",e);
    return new ResponseEntity<>("incorrect username/pswd",HttpStatus.BAD_REQUEST);
}}}
