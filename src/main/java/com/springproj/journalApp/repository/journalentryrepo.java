
package com.springproj.journalApp.repository;

import com.springproj.journalApp.entity.journalentry;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalentryrepo extends MongoRepository <journalentry, ObjectId> {
//every thing is in the mongorepository which we extend

}


//mongorepository-- interface given by springdatamongodb in pom xm does cred opretion
//pojo = plain old java object bean is a speacialised pojo
