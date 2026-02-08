
package com.springproj.journalApp.service;

import com.springproj.journalApp.controller.journalentrycontrollerv2;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.userentryrepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;//abstraction above logback (simpe logging facade for java)
import org.slf4j.LoggerFactory;
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


    public void saveuser(user u){
        userentryrepo.save(u);

    }



    public void saveadmin(user u) {
        u.setPassword(passwordencoder.encode(u.getPassword()));
        u.setRoles(Arrays.asList("USER","ADMIN"));
        userentryrepo.save(u);
    }
    private static final PasswordEncoder passwordencoder= new BCryptPasswordEncoder();

    public boolean savenewuser(user u) {
        try {
            u.setPassword(passwordencoder.encode(u.getPassword()));
            u.setRoles(Arrays.asList("user"));
            userentryrepo.save(u);
            return true;
        } catch (Exception e) {
            log.info("xyz");
//            logger.warn("xyz");
//            logger.debug("xyz");
//            logger.trace("xyz");
//            logger.error("error occured ",e);
//
//


            return false;

        }
    }


    public List<user> getall(){
        return userentryrepo.findAll();

    }
    public Optional<user> findbyid(ObjectId id) {
        return userentryrepo.findById(id);
    }

    public void deltebyid(ObjectId id)
    {
        userentryrepo.deleteById(id);
    }


    public user findByusername(String username){
        return userentryrepo.findByUsername(username);


    }



}
