package com.springproj.journalApp.controller;

import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class admincontroller {
    @Autowired
    private userservice userservice;

    @GetMapping
    public ResponseEntity<List<user>> getAllUsers() {

        List<user> all = userservice.getall();

        if (all == null || all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(all);
    }
    @PostMapping("create-admin-user")
    public void createuser(@RequestBody user user){
        userservice.saveadmin(user);
    }
}
