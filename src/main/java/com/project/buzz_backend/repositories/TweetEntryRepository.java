package com.example.jounal.repositories;

import com.example.jounal.entities.TweetEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetEntryRepository extends MongoRepository<TweetEntry , ObjectId> {
}
