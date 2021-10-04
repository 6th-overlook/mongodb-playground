package com.travelport.mongodbplayground.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoQuery {
    private String collectionName;
    private Document queryDocument;
}