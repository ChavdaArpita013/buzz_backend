package com.example.jounal.services;

import com.example.jounal.entities.Hashtag;
import com.example.jounal.entities.Users;
import com.example.jounal.repositories.HashTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HashTagService {

    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private HashTagRepository hashTagRepository;

//    @Transactional
    public ObjectId processHashTag(String hashtag, String userName){
        try {
            Users user = userEntryService.findByUserName(userName);
            Optional<Hashtag> tagName = Optional.ofNullable(hashTagRepository.findByHashtag(hashtag));

            if(tagName.isPresent()){
                Hashtag existingTag = tagName.get();
                //already exists then add userId and increase the number
                existingTag.getUsedBy().add(user.getId());
                existingTag.setBuzzCount(existingTag.getBuzzCount() + 1);
                Hashtag save = hashTagRepository.save(existingTag);
                return save.getId();
            }else {
                Hashtag newTag = new Hashtag();

                //if tag nt exists create new one
                newTag.setHashtag(hashtag);
                newTag.setBuzzCount(1);
                newTag.setCreatedBy(userName);
                newTag.setDate(LocalDateTime.now());
                newTag.getUsedBy().add(user.getId());

                Hashtag save = hashTagRepository.save(newTag);
                return save.getId();
            }

        } catch (Exception e) {
            log.error("error creating hash TAG");
            throw new RuntimeException("Error creating hash tag" , e);
        }
    }


    public List<Hashtag> getAllHashtags(){
        return hashTagRepository.findAll();
    }

    public List<Hashtag> getTrendingHashTags(){
        List<Hashtag> allHashTags = hashTagRepository.findAllByOrderByBuzzCountDesc();
        return allHashTags;
    }
}
