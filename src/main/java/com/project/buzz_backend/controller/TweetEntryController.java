package com.example.jounal.controller;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.example.jounal.entities.TweetEntry;
import com.example.jounal.entities.Users;
import com.example.jounal.services.TweetEntryService;
import com.example.jounal.services.UserEntryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/buzz")
public class TweetEntryController {

    @Autowired
    private TweetEntryService tweetEntryService;
    @Autowired
    private UserEntryService userEntryService;

//    public Map<Long , TweetEntry> tweetEntries= new HashMap<>();
    @GetMapping
    public List<TweetEntry> getAllTweets(){
        return tweetEntryService.getAll();
    }
    @GetMapping("/user")
    public ResponseEntity<?> getTweetEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userEntryService.findByUserName(userName);
        List<TweetEntry> all = user.getTweetEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<TweetEntry> getTweetEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userEntryService.findByUserName(userName);
        List<TweetEntry> collect = user.getTweetEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<TweetEntry> tweetEntry = tweetEntryService.getTweetById(myId);
            if(tweetEntry.isPresent()){
                return new ResponseEntity<>(tweetEntry.get() , HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<TweetEntry> createTweet(@RequestBody TweetEntry entry){
       try {
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String userName = authentication.getName();
           tweetEntryService.saveTweet(entry , userName);
           return new ResponseEntity<>(entry , HttpStatus.OK);
       } catch (Exception e) {
           return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

       }
    }
        @PutMapping("{id}")
        public ResponseEntity<?> updateLikes(@PathVariable ObjectId id,@RequestBody Map<String, String> body){
            try {
                String userName = body.get("userName");
                Optional<TweetEntry> tweetEntry = tweetEntryService.saveLikes(id , userName);
                log.info(String.valueOf(tweetEntry));
                if(tweetEntry.isPresent()){
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteTweet(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean isRemoved = tweetEntryService.deleteTweetById(id, userName);
        if(isRemoved){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("id/{id}")
    public ResponseEntity<?> updateTweet(@PathVariable ObjectId id,
                                         @RequestBody TweetEntry newtweet){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user= userEntryService.findByUserName(userName);
        List<TweetEntry> collect = user.getTweetEntries().stream().filter(x ->x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<TweetEntry> tweetEntry = tweetEntryService.getTweetById(id);
            if(tweetEntry.isPresent()){
                TweetEntry old = tweetEntry.get();
                old.setTitle(newtweet.getTitle() != null && !newtweet.getTitle().equals("") ? newtweet.getTitle() : old.getTitle());
                old.setTweet_content(newtweet.getTweet_content() != null && !newtweet.getTweet_content().equals("") ? newtweet.getTweet_content() : old.getTweet_content());
                tweetEntryService.saveTweet(old);
                return new ResponseEntity<>(old , HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
