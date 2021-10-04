package com.travelport.mongodbplayground.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.travelport.mongodbplayground.configuration.properties.MongoClientProperties;
import com.travelport.mongodbplayground.configuration.properties.MongoDAOProperties;
import com.travelport.mongodbplayground.dao.MongoDAO;
import com.travelport.mongodbplayground.model.Student;
import com.travelport.mongodbplayground.model.Yearbook;
import com.travelport.mongodbplayground.utilties.YearbookUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.eq;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
@Slf4j
public class ChangeStreamPlayground {

    static MongoClient mongoClient;

    static MongoDAO mongoDAO;

    public static String collectionName;


    public static MongoClient createMongoClient(MongoClientProperties mongoClientProperties) {
        ConnectionString connectionString = new ConnectionString(mongoClientProperties.getConnectionString());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(settings);
    }

    @BeforeAll
    public static void setUpDb() {
        collectionName = "csplayground";
        mongoClient = createMongoClient(new MongoClientProperties("mongodb://localhost:27017"));
        mongoDAO = new MongoDAO(mongoClient, new MongoDAOProperties("integration-test"));

    }

    @BeforeEach
    public void setUp() {
        var resMono = mongoDAO.dropCollection(collectionName);
        StepVerifier.create(resMono)
                .assertNext(x -> assertTrue(x.wasAcknowledged()))
                .verifyComplete();
    }


    public void insertYearbook(Yearbook yearbook) {
        try {
            var mongoCollection = mongoDAO.getCollection(collectionName);
            var yearbookDoc = Document.parse(yearbook.toJson());
            mongoDAO.insertMany(mongoCollection, List.of(yearbookDoc));
        } catch (JsonProcessingException e) {
        }
    }

    public void updateYearbook(Yearbook yearbook) {
        try {
            var mongoCollection = mongoDAO.getCollection(collectionName);
            var yearbookDoc = Document.parse(yearbook.toJson());
            var query = new Document("uniqueId", new Document("$eq", yearbook.getUniqueId()));

            mongoDAO.updateOne(mongoCollection, query, yearbookDoc);
        } catch (JsonProcessingException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddChangeStream() throws JsonProcessingException {
        var yearbook = Document.parse(YearbookUtils.getYearbook().toJson());
        var csFlux = mongoDAO.createChangeStream(collectionName, List.of()).take(1);
        var ses = Executors.newSingleThreadScheduledExecutor();
        ses.schedule(() ->
                mongoDAO.insertMany(mongoDAO.getCollection(collectionName), List.of(yearbook)), 200, TimeUnit.MILLISECONDS);
        StepVerifier.create(csFlux)
                .assertNext(x -> assertThat(x.getFullDocument(), is(notNullValue())))
                .verifyComplete();
    }


    @Test
    public void testChangeStreamUpdateField() {
        var csFlux = mongoDAO.createChangeStream(collectionName, List.of()).log().take(2);
        var ses = Executors.newSingleThreadScheduledExecutor();
        ses.schedule(() -> {
            var yearbook = YearbookUtils.getYearbook();
            insertYearbook(yearbook);
            yearbook.setSchool("Cool School");
            log.info("Insert complete");
            updateYearbook(yearbook);
            log.info("Update complete");
        }, 200, TimeUnit.MILLISECONDS);
        StepVerifier.create(csFlux)
                .assertNext(x -> assertThat(x.getFullDocument(), is(notNullValue())))
                .assertNext(x -> assertThat(x.getFullDocument(), is(nullValue())))
                .verifyComplete();
    }

    @Test
    public void testChangeStreamUpdates() {
        var csFlux = mongoDAO.createChangeStream(collectionName, List.of()).log().take(2);
        var ses = Executors.newSingleThreadScheduledExecutor();
        ses.schedule(() -> {
            var yearbook = YearbookUtils.getYearbook();
            insertYearbook(yearbook);
            log.info("Insert complete");
            var greg = new Student("Greg", 17, List.of("swimming", "biking"));
            try {
                yearbook.addStudent(greg);
                updateYearbook(yearbook);
                log.info("Update complete");
            } catch (Exception e) {
                log.info("Exceptions: {}", e);
            }
        }, 200, TimeUnit.MILLISECONDS);
        StepVerifier.create(csFlux)
                .assertNext(x -> assertThat(x.getFullDocument(), is(notNullValue())))
                .assertNext(x -> assertThat(x.getFullDocument(), is(nullValue())))
                .verifyComplete();
    }

    @Test
    public void testChangeStreamUpdateBensAge() {
        var csFlux = mongoDAO.createChangeStream(collectionName, List.of()).log().take(2);
        var ses = Executors.newSingleThreadScheduledExecutor();
        ses.schedule(() -> {
            var yearbook = YearbookUtils.getYearbook();
            insertYearbook(yearbook);
            log.info("Insert complete");
            try {
                yearbook.getPeople().get(2).setAge(19);
                updateYearbook(yearbook);
                log.info("Update complete");
            } catch (Exception e) {
                log.info("Exceptions: {}", e);
            }
        }, 200, TimeUnit.MILLISECONDS);
        StepVerifier.create(csFlux)
                .assertNext(x -> assertThat(x.getFullDocument(), is(notNullValue())))
                .assertNext(x -> assertThat(x.getFullDocument(), is(nullValue())))
                .verifyComplete();
    }

}
