package com.example.jounal.repositories;

import com.example.jounal.entities.TweetEntry;
import com.example.jounal.entities.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserEntryRepository extends MongoRepository<Users, ObjectId> {
    Users findByUserName(String userName);
    List<String> findAllUserName();

    void deleteByUserName(String userName);
}
