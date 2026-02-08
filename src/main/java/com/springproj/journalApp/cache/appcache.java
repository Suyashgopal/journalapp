package com.springproj.journalApp.cache;


import com.springproj.journalApp.repository.configJournalAppRepo;
import com.springproj.journalApp.entity.configjournalappentity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class appcache {


    @Autowired
    private configJournalAppRepo  configJournalAppRepo;
    public Map<String,String > Appcache= new HashMap<>();


    @PostConstruct
    public void init(){
        Appcache= new HashMap<>();
        List<configjournalappentity> all=configJournalAppRepo.findAll();
     for(configjournalappentity configjournalappentity: all){
         Appcache.put(configjournalappentity.getKey(),configjournalappentity.getValue());

     }

    }

}
