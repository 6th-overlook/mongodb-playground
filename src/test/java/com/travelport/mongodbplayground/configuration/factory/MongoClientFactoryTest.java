package com.travelport.mongodbplayground.configuration.factory;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
class MongoClientFactoryTest {

    @Autowired
    MongoClient mongoClient;

    void testMongoClient() {
        assertThat(mongoClient, is(notNullValue()));
    }

}