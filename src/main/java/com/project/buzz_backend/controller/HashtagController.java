package com.example.jounal.controller;

import com.example.jounal.entities.Hashtag;
import com.example.jounal.services.HashTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hashtag")
public class HashtagController {
    @Autowired
    private HashTagService hashTagService;
    @GetMapping
    private List<Hashtag> getAllHashtags(){
        return hashTagService.getAllHashtags();
    }

    @PostMapping
    private ResponseEntity<?> createHashTag(@RequestBody Hashtag hashtag){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            hashTagService.processHashTag(hashtag.getHashtag() , userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/trending")
    private List<Hashtag> getTrendingHashTags(){
        try {
            List<Hashtag> trendingHashTags = hashTagService.getTrendingHashTags();
            return trendingHashTags;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
