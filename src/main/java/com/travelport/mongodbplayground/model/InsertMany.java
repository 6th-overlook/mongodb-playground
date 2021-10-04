package com.travelport.mongodbplayground.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InsertMany {
    private String collectionName;
    private List<Document> insertList;
}