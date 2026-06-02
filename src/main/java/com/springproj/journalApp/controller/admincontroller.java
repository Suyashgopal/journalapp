package com.springproj.journalApp.controller;

import com.springproj.journalApp.dto.SignupRequest;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class admincontroller {
    @Autowired
    private userservice userservice;

    @GetMapping
    public ResponseEntity<Page<user>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<user> all = userservice.getall(PageRequest.of(page, Math.min(size, 100)));

        if (all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(all);
    }
    @PostMapping("create-admin-user")
    public ResponseEntity<String> createuser(@Valid @RequestBody SignupRequest request) {
        userservice.saveadmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin user created successfully");
    }
}
