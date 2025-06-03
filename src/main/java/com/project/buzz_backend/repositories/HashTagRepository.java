package com.example.jounal.repositories;

import com.example.jounal.entities.Hashtag;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HashTagRepository extends MongoRepository<Hashtag , ObjectId> {

    Hashtag findByHashtag(String hashtag);
    //gives all hashtags in decs order
    List<Hashtag> findAllByOrderByBuzzCountDesc();
}
