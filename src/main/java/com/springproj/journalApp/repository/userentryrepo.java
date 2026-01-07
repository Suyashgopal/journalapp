package com.springproj.journalApp.repository;

import com.springproj.journalApp.entity.user;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userentryrepo extends MongoRepository<user, ObjectId> {

    user findByUsername(String username);
}
