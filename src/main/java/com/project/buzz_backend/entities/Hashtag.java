package com.example.jounal.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "hashtags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hashtag {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String hashtag;
    private String createdBy; // user who created this hashtag
    private LocalDateTime date; // date & time when hashtag is created
    private Set<ObjectId> usedBy = new HashSet<>(); // how many user's tweets includes this hashtag
    private int buzzCount; //count no buzz having this hashtag
}
