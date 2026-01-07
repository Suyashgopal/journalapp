package com.springproj.journalApp.controller;

import com.springproj.journalApp.entity.journalentry;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.service.journalentryservice;
import com.springproj.journalApp.service.userservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class journalentrycontrollerv2 {
@Autowired
private journalentryservice journalentryservice;
@Autowired
private userservice userservice;




    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {

        user userEntity = userservice.findByusername(username);

        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        List<journalentry> entries = userEntity.getJournalentries();

        if (entries == null || entries.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.ok(entries);
    }




 @PostMapping("{username}")
  public ResponseEntity<journalentry> createentry(@RequestBody journalentry myentry,@PathVariable String username ){
        try{

            myentry.setDate(LocalDateTime.now());
            journalentryservice.saveentry(myentry,username);
            return  new ResponseEntity<>(myentry,HttpStatus.CREATED);

        }
        catch(Exception e){

            return  new ResponseEntity<>(myentry,HttpStatus.BAD_REQUEST);

        }



    }



    @GetMapping("/id/{myid}")
    public ResponseEntity<journalentry> getjournalentrybyid(@PathVariable ObjectId myid) {

        Optional<journalentry> journalentry = journalentryservice.findbyid(myid);

        if (journalentry.isPresent()) {
            return new ResponseEntity<>(journalentry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




    @PutMapping("/{username}/id/{myid}")
    public ResponseEntity<?> updatejournalbyid(
            @PathVariable("myid") ObjectId id,
            @RequestBody journalentry newentry,
             @PathVariable String username) {
   user user= userservice.findByusername(username);
   if(user==null){
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        Optional<journalentry> old = journalentryservice.findbyid(id);

        if (old.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }

        journalentry existing = old.get();

        if (newentry.getTitle() != null && !newentry.getTitle().isEmpty()) {
            existing.setTitle(newentry.getTitle());
        }

        if (newentry.getContent() != null && !newentry.getContent().isEmpty()) {
            existing.setContent(newentry.getContent());
        }

        journalentryservice.saveentry(existing);
        return ResponseEntity.ok(existing); // 200
    }


    //what ever data is given in new entry ony thta data is updated

//check  howto debug code

}
