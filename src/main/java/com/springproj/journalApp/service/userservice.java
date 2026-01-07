
package com.springproj.journalApp.service;

import com.springproj.journalApp.entity.journalentry;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.journalentryrepo;
import com.springproj.journalApp.repository.userentryrepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
//business logic ie. sari katha funtionalities yaha hogi
public class userservice {
    @Autowired
    private userentryrepo userentryrepo;


    public void saveentry(user u){
        userentryrepo.save(u);

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
