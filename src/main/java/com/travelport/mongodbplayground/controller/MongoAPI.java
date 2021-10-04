package com.travelport.mongodbplayground.controller;


import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;
import com.travelport.mongodbplayground.model.InsertMany;
import com.travelport.mongodbplayground.model.MongoQuery;
import com.travelport.mongodbplayground.model.Update;
import com.travelport.mongodbplayground.service.MongoService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("mongo")
@Slf4j
public class MongoAPI {

    @Autowired
    MongoService mongoService;

    @PostMapping("/find-all")
    public Flux<Document> findAll(@RequestBody MongoQuery mongoQuery) {
        return mongoService.findAll(mongoQuery.getCollectionName(), mongoQuery.getQueryDocument());
    }

    @PostMapping("/find-one")
    public Mono<Document> findOne(@RequestBody MongoQuery mongoQuery) {
        return mongoService.findOne(mongoQuery.getCollectionName(), mongoQuery.getQueryDocument());
    }

    @PostMapping("/count-results")
    public Mono<Long> countResults(@RequestBody MongoQuery mongoQuery) {
        return mongoService.countResults(mongoQuery.getCollectionName(), mongoQuery.getQueryDocument());
    }

    @PostMapping("/insert-many")
    public Mono<InsertManyResult> insertMany(@RequestBody InsertMany insertMany) {
        log.info("Request: {}", insertMany.toString());
        return mongoService.insertMany(insertMany.getCollectionName(), insertMany.getInsertList());
    }


    @PostMapping("/update-many")
    public Mono<UpdateResult> updateMany(@RequestBody Update update) {
        log.info("Request: {}", update.toString());
        return mongoService.updateMany(update.getCollectionName(), update.getFilterQuery(), update.getUpdateOperation());
    }

    @PostMapping("/update-one")
    public Mono<UpdateResult> updateOne(@RequestBody Update update) {
        log.info("Request: {}", update.toString());
        return mongoService.updateOne(update.getCollectionName(), update.getFilterQuery(), update.getUpdateOperation());
    }
}
