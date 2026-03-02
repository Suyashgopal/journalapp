package com.springproj.journalApp.controller;

import com.springproj.journalApp.entity.journalentry;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.service.journalentryservice;
import com.springproj.journalApp.service.userservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalentrycontrollerv2 {
@Autowired
private journalentryservice journalentryservice;
@Autowired
private userservice userservice;



//as now journal services are password protected we now have to make some changes
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
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




 @PostMapping
  public ResponseEntity<journalentry> createentry(@RequestBody journalentry myentry ){
        try{  Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
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

        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        user user= userservice.findByusername(username);
        List<journalentry> collect = user.getJournalentries().stream().filter(x-> x.getId().equals(myid)).collect(Collectors.toList());

        if (!collect.isEmpty()) {
            return new ResponseEntity<>(collect.get(0), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }




    @PutMapping("/id/{myid}")
    public ResponseEntity<?> updatejournalbyid(
            @PathVariable("myid") ObjectId id,
            @RequestBody journalentry newentry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        user user = userservice.findByusername(username);
        List<journalentry> collect = user.getJournalentries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<journalentry> journalentry = journalentryservice.findbyid(id);
            if (journalentry.isPresent()) {
                journalentry old = journalentry.get();

                old.setTitle(newentry.getTitle() != null && !newentry.getTitle().equals("") ? newentry.getTitle() : old.getTitle());
                old.setContent(newentry.getContent() != null && !newentry.getContent().equals("") ? newentry.getContent() : old.getContent());
                journalentryservice.saveentry(old);
                return new ResponseEntity<>(journalentry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }



    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deletehournalentrybyid(@PathVariable ObjectId myid)
    {Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        boolean removed= journalentryservice.deltebyid(myid,username);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //what ever data is given in new entry ony thta data is updated

//check  howto debug code

}
