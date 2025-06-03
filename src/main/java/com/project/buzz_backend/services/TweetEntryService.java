package com.example.jounal.services;

import com.example.jounal.entities.TweetEntry;
import com.example.jounal.entities.Users;
import com.example.jounal.repositories.TweetEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class TweetEntryService {
    @Autowired
    private TweetEntryRepository tweetEntryRepository;
    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private HashTagService hashTagService;
//    @Transactional
    public void saveTweet(TweetEntry tweetEntry, String userName){
        try {
            Users user = userEntryService.findByUserName(userName);
            List<String> hashtagNames= tweetEntry.getHashtagNames();

            Set<ObjectId> hashtagIds = new HashSet<>();
            for(String hashtag : tweetEntry.getHashtagNames()){
                ObjectId tagId = hashTagService.processHashTag(hashtag, userName);
                hashtagIds.add(tagId);
            }
            tweetEntry.setHashtagNames(hashtagNames);
            tweetEntry.setHashtags(hashtagIds);
            tweetEntry.setDate(LocalDateTime.now());
            tweetEntry.setCreatedBy(userName);
            TweetEntry saved = tweetEntryRepository.save(tweetEntry);
            user.getTweetEntries().add(saved);
            userEntryService.saveUser(user);
        } catch (Exception e) {
            log.error("Error saving tweet" , e);
            throw new RuntimeException("Something went wrong in saveTweet tweetentryservice" , e);
        }
    }
    public void saveTweet(TweetEntry tweetEntry){
        try {
            tweetEntryRepository.save(tweetEntry);
        } catch (Exception e) {
            log.error("Error saving tweet" , e);
        }
    }

    public Optional<TweetEntry> saveLikes(ObjectId id , String userName){
        //find tweet with object id
        Optional<TweetEntry> tweetById = tweetEntryRepository.findById(id);
        log.info(userName , id);

        if(tweetById.isPresent()){
            TweetEntry tweet = tweetById.get();
            log.info(String.valueOf(tweet));
            boolean add = tweet.getLikes().add(userName);
            log.info(String.valueOf(add));
            if(add){
                tweetEntryRepository.save(tweet);//when userName added to likes array save tweet entity
            }
        }
        return tweetById;
    }

    public List<TweetEntry> getAll(){
        return tweetEntryRepository.findAll();
    }

    public Optional<TweetEntry> getTweetById(ObjectId id){
        return tweetEntryRepository.findById(id);
    }

    public boolean deleteTweetById(ObjectId id, String userName){
        boolean isRemoved = false;
        try {
            Users user = userEntryService.findByUserName(userName);
            isRemoved = user.getTweetEntries().removeIf(x -> x.getId().equals(id));
            if(isRemoved){
                userEntryService.saveUser(user);
                tweetEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting tweet by Id",e);
        }
        return isRemoved;

    }

    public Optional<TweetEntry> updateTweetById(ObjectId id){
        return tweetEntryRepository.findById(id);
    }
}
