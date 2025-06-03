package com.example.jounal.repositories;

import com.example.jounal.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Users> getUserForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is("Arpita"));
        List<Users> users = mongoTemplate.find(query , Users.class);
        return users;
    }
}
