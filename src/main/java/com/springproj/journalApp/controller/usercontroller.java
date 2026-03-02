package com.springproj.journalApp.controller;

import com.springproj.journalApp.api.response.weatherResponse;
import com.springproj.journalApp.cache.appcache;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.userentryrepo;
import com.springproj.journalApp.service.userservice;
import com.springproj.journalApp.service.weatherservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;
//here we have secured endpts
@RestController
@RequestMapping("/user")
public class usercontroller {
    @Autowired
    private userservice userservice;
    @Autowired
    private userentryrepo userentryrepo;
    @Autowired
    weatherservice weatherservice;
    @Autowired
    private PasswordEncoder passwordEncoder;




    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<user> getallusers(){
return userservice.getall();

    }





    @PutMapping
    public ResponseEntity<?> updateuser( @RequestBody user user) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        user userindb = userservice.findByusername(username);
if(userindb!= null){
    userindb.setUsername(user.getUsername());//database wale user ka username update ho raha h body m given username se
    userindb.setPassword(passwordEncoder.encode(user.getPassword()));
    userservice.saveUpdatedUser(userindb);
}

   return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

@DeleteMapping
  public ResponseEntity<?> deleteUserById(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        userentryrepo.deleteByUsername(auth.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}


    @GetMapping("/new")
    public ResponseEntity<?> greetings(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        weatherResponse weatherResponse= weatherservice.getWether("mumbai");
        String greeting ="";

        if (weatherResponse!= null){
            greeting=",weather feels like"+ weatherResponse.getCurrent().getFeelslike();

        }



        return new ResponseEntity<>("hi "+ auth.getName()+greeting,HttpStatus.OK );
    }

    @Autowired
    appcache appcache;
    @GetMapping("clear-app-cache")
    public void clearappcache(){
        appcache.init();
    }


    }





