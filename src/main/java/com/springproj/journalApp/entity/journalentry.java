package com.springproj.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")
//what is document
@Data
@NoArgsConstructor

//data includes
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor


public class journalentry {
    @Id //mapped as a primary key(unique key

   @NonNull

    private ObjectId id;//objectid is the datatype of mongodb id

    private String title;
    private String content;
    private LocalDateTime date;



}
