package com.springproj.journalApp.entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
//document tells this class should be a document

@Document(collection = "users")
@Data

public class user {
    @Id
    private ObjectId id;
    @Indexed(unique = true)//we still had to set a property in .properties check it out
    @NonNull
    private String username;
    @NonNull
    private String password;

      @DBRef
    private List<journalentry> journalentries= new ArrayList<>();

    private List<String> roles;
    //every user will have diffn roles


}
