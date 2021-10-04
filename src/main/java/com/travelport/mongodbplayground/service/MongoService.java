package com.travelport.mongodbplayground.service;

import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;
import com.travelport.mongodbplayground.dao.MongoDAO;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MongoService {

    @Autowired
    MongoDAO mongoDAO;

    public Flux<Document> findAll(String collectionName, Document query) {
            var collectionHandle = mongoDAO.getCollection(collectionName);
            return mongoDAO.findAll(collectionHandle, query);
    }


    public Mono<Long> countResults(String collectionName, Document query) {
        var collectionHandle = mongoDAO.getCollection(collectionName);
        return mongoDAO.countResults(collectionHandle, query);
    }


    public Mono<InsertManyResult> insertMany(String collectionName, List<Document> insertList) {
        var collectionHandle = mongoDAO.getCollection(collectionName);
        return mongoDAO.insertMany(collectionHandle, insertList);
    }

    public Mono<UpdateResult> updateMany(String collectionName, Document filterQuery, Document updateOperation) {
        var collectionHandle = mongoDAO.getCollection(collectionName);
        return mongoDAO.updateMany(collectionHandle, filterQuery, updateOperation);
    }


    public Mono<UpdateResult> updateOne(String collectionName, Document filterQuery, Document updateOperation) {
        var collectionHandle = mongoDAO.getCollection(collectionName);
        return mongoDAO.updateOne(collectionHandle, filterQuery, updateOperation);
    }

    public Mono<Document> findOne(String collectionName, Document query) {
        var collectionHandle = mongoDAO.getCollection(collectionName);
        return mongoDAO.findOne(collectionHandle, query);
    }
}
