package com.travelport.mongodbplayground.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;
import com.travelport.mongodbplayground.configuration.properties.MongoDAOProperties;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class MongoDAO {

    MongoDatabase mongoDatabase;

    @Autowired
    public MongoDAO(MongoClient mongoClient, MongoDAOProperties mongoDAOProperties) {
        mongoDatabase = mongoClient.getDatabase(mongoDAOProperties.getDatabase());
    }

    public MongoCollection getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }


   public Flux<Document> findAll(MongoCollection collectionHandle, Document query) {
        return Flux.fromIterable(collectionHandle.find(query));
   }

   public Mono<Long> countResults(MongoCollection mongoCollection, Document query) {
        return Mono.just(mongoCollection.countDocuments(query));
   }

   public Mono<InsertManyResult> insertMany(MongoCollection mongoCollection, List<Document> documentList) {
        return Mono.just(mongoCollection.insertMany(documentList));
   }

   public Mono<UpdateResult> updateMany(MongoCollection mongoCollection, Document query, Document updateOperation) {
        return Mono.just(mongoCollection.updateMany(query, updateOperation));
   }

    public Mono<UpdateResult> updateOne(MongoCollection mongoCollection, Document query, Document updateOperation) {
        var document = new Document("$set", updateOperation);
        return Mono.just(mongoCollection.updateOne(query, document));
    }

    public Mono<Document> findOne(MongoCollection mongoCollection, Document query) {

        var cursor = mongoCollection.find(query).cursor();
        if(cursor.hasNext()) {
            return Mono.just((Document) cursor.next());
        } else {
            return Mono.empty();
        }
    }

    public Mono<DeleteResult> dropCollection(String collectionName) {
        var queryString = "{}";
        var collection = mongoDatabase.getCollection(collectionName);
        return Mono.just(collection.deleteMany(Document.parse(queryString)));
    }

    public Flux<ChangeStreamDocument> createChangeStream(String collectionName, List<Document> pipeline) {
        var collection = mongoDatabase.getCollection(collectionName);
        return Flux.fromIterable(collection.watch(pipeline));
    }

}