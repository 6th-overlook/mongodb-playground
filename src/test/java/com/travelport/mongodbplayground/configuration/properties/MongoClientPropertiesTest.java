package com.travelport.mongodbplayground.configuration.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = {MongoClientProperties.class})
class MongoClientPropertiesTest {

    @Autowired
    MongoClientProperties mongoClientProperties;

    @Test
    void testMongoClientProperties() {
        assertThat(mongoClientProperties, is(notNullValue()));
        assertThat(mongoClientProperties.getConnectionString(), is(not(emptyOrNullString())));
    }
}