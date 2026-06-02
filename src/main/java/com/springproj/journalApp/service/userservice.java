
package com.springproj.journalApp.service;

import com.springproj.journalApp.dto.SignupRequest;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.userentryrepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
//business logic ie. sari katha funtionalities yaha hogi
@Slf4j // using this annotaion to avoud making ogger instance again and again
//    private static final Logger logger = LoggerFactory.getLogger(journalentrycontrollerv2.class);
public class userservice {

    @Autowired
    private userentryrepo userentryrepo;

    // Share the single Spring-managed encoder instead of a private static instance.
    @Autowired
    private PasswordEncoder passwordEncoder;


    public void saveuser(user u){
        userentryrepo.save(u);

    }



    public void saveadmin(SignupRequest req) {
        user u = user.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .sentimentAnalysis(req.isSentimentAnalysis())
                .roles(Arrays.asList("USER", "ADMIN"))
                .build();
        userentryrepo.save(u);
    }

    /**
     * Creates a normal user. A duplicate username surfaces as a DuplicateKeyException,
     * which the GlobalExceptionHandler translates to 409 — no more silent boolean swallow.
     */
    public user savenewuser(SignupRequest req) {
        user u = user.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .sentimentAnalysis(req.isSentimentAnalysis())
                .roles(Arrays.asList("USER"))
                .build();
        return userentryrepo.save(u);
    }


    public List<user> getall(){
        return userentryrepo.findAll();

    }

    // Paginated listing so the admin endpoint never loads the entire collection at once.
    public org.springframework.data.domain.Page<user> getall(org.springframework.data.domain.Pageable pageable){
        return userentryrepo.findAll(pageable);
    }
    public Optional<user> findbyid(ObjectId id) {
        return userentryrepo.findById(id);
    }

    public void deltebyid(ObjectId id)
    {
        userentryrepo.deleteById(id);
    }


    public void saveUpdatedUser(user u) {
        userentryrepo.save(u);
    }

    public user findByusername(String username){
        return userentryrepo.findByUsername(username);


    }



}
