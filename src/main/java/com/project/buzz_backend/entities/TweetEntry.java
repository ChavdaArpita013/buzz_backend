package com.example.jounal.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "tweet_entries")
@Data
@NoArgsConstructor
public class TweetEntry {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @NonNull
    private String title;
    private String tweet_content;
    private String mediaUrl;
    private LocalDateTime date;
    private String createdBy;
    private Set<ObjectId> hashtags = new HashSet<>();

    private List<String> hashtagNames = new ArrayList<>();

    private Set<String> likes = new HashSet<>();
}
