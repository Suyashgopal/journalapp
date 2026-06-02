package com.springproj.journalApp.repository;
import org.springframework.security.core.userdetails.User;

import com.springproj.journalApp.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class userRepoimpl {

    @Autowired
    private MongoTemplate MongoTemplate;


    public List<user> getuserforsa(){
        Query query= new Query();

        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"));//query and criteria is used
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));// must match the persisted field name exactly (was "sentimentanalysis" → never matched)
        query.addCriteria(Criteria.where("roles").in("USER","ADMIN"));



//        Criteria  criteria= new Criteria();
//not in= nin

//either any 2 of them exisist
//        query.addCriteria(criteria.orOperator(
//                Criteria.where("email").exists(true),
//                Criteria.where("sentimentanalysis").is(true)));

//query.addCriteria(Criteria.where("feild").isNull())
//        query.addCriteria(Criteria.where("feild").maxDistance())
//        query.addCriteria(Criteria.where("feild").near())

// less than equal to -lte,greater than equal to --gte,


        List<user> users = MongoTemplate.find(query, user.class);
        return users;



    }
}
