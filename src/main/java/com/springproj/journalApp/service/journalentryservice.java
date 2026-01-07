package com.springproj.journalApp.service;

import com.springproj.journalApp.entity.journalentry;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.journalentryrepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Component
//business logic ie. sari katha funtionalities yaha hogi
public class journalentryservice {
@Autowired
    private journalentryrepo  journalentryrepo;
//this is a interface spring makes its implimentation during run time

@Autowired
private userservice userservice;
@Transactional
    public void saveentry(journalentry je, String username){
user user = userservice.findByusername(username);
//what if the user isnt saved ??-- the entry wil still be saved but not in any user we dont want this
        //so we will make the whole method a transaction

        journalentry saved = journalentryrepo.save(je);
        user.getJournalentries().add(saved);
        userservice.saveentry(user);

    }

    public List<journalentry> getall(){
     return journalentryrepo.findAll();

    }
    public Optional<journalentry> findbyid(ObjectId id) {
        return journalentryrepo.findById(id);
    }

    public void deltebyid(ObjectId id, String username){
        user user = userservice.findByusername(username);
       user.getJournalentries().removeIf(x->x.getId().equals(id));//this is lamda expression--java 8
        //replacement of above
//        Iterator<journalentry> iterator = user.getJournalentries().iterator();
//
//        while (iterator.hasNext()) {
//            JournalEntry entry = iterator.next();
//            if (entry.getId().equals(id)) {
//                iterator.remove();
//            }
//        }

userservice.saveentry(user);
        journalentryrepo.deleteById(id);
}



//overloading
public void saveentry(journalentry je){

journalentryrepo.save(je);

}





}
//controller call karega service ko service call karega repo ko
//controller--->  service--->repository