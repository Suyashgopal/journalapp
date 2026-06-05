package com.springproj.journalApp.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class user {
    @Id
    private ObjectId id;
    @Indexed(unique = true)//we still had to set a property in .properties check it out
    @NonNull
    private String username;
    private String email;
    private boolean sentimentAnalysis;


    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // never serialize the password hash in API responses
    private String password;

      @DBRef
    private List<journalentry> journalentries= new ArrayList<>();

    private List<String> roles;
    //every user will have diffn roles


}
