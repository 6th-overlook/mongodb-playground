package com.travelport.mongodbplayground.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Update {
    private String collectionName;
    private Document filterQuery;
    private Document updateOperation;
}
