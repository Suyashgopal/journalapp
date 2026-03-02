package com.springproj.journalApp.service;

import com.springproj.journalApp.entity.journalentry;
import com.springproj.journalApp.entity.user;
import com.springproj.journalApp.repository.journalentryrepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
//business logic ie. sari katha funtionalities yaha hogi
public class journalentryservice {
@Autowired
    private journalentryrepo  journalentryrepo;
//this is a interface spring makes its implimentation during run time




@Autowired
private userservice userservice;
@Transactional
    public void saveentry(journalentry je, String username){
    try{
user user = userservice.findByusername(username);
//what if the user isnt saved ??-- the entry wil still be saved but not in any user we dont want this
        //so we will make the whole method a transaction

        journalentry saved = journalentryrepo.save(je);
        user.getJournalentries().add(saved);
        userservice.saveuser(user);}
    catch(Exception e){
        log.error("Error occurred while saving entry", e);
        throw new RuntimeException("an error occoured while saving the entry",e);

    }

    }

    public List<journalentry> getall(){
     return journalentryrepo.findAll();

    }
    public Optional<journalentry> findbyid(ObjectId id) {
        return journalentryrepo.findById(id);
    }
@Transactional
    public boolean deltebyid(ObjectId id, String username){
     boolean removed= false;
      try{
          user user = userservice.findByusername(username);
           removed=  user.getJournalentries().removeIf(x->x.getId().equals(id));//this is lamda expression--java 8
          //replacement of above
//        Iterator<journalentry> iterator = user.getJournalentries().iterator();
//
//        while (iterator.hasNext()) {
//            JournalEntry entry = iterator.next();
//            if (entry.getId().equals(id)) {
//                iterator.remove();
//            }
//        }
          if(removed){
              userservice.saveuser(user);
              journalentryrepo.deleteById(id);
          }


      }
      catch (Exception e){
          log.error("Error occurred while deleting entry with id: {}", id, e);
          throw new RuntimeException("a error occured while deleting the entry");
      }
    return removed;


}



//overloading
public void saveentry(journalentry je){

journalentryrepo.save(je);

}






}
//controller call karega service ko service call karega repo ko
//controller--->  service--->repository