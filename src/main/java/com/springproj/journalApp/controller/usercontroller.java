package com.springproj.journalApp.controller;

import com.springproj.journalApp.entity.journalentry;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.service.userservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/user")
public class usercontroller {
    @Autowired
    private userservice userservice;

@GetMapping
    public List<user> getallusers(){
return userservice.getall();

    }

    @PostMapping
    public void  createentry(@RequestBody user userbody){

            userservice.saveentry(userbody);

    }



    @PutMapping("/{username}")
    public ResponseEntity<?> updateuser( @RequestBody user user,@PathVariable String username) {
        user userindb = userservice.findByusername(username);
if(userindb!= null){
    userindb.setUsername(user.getUsername());//database wale user ka username update ho raha h body m given username se
    userindb.setPassword(user.getPassword());
    userservice.saveentry(userindb);
}

   return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }




    }





