package com.travelport.mongodbplayground.configuration.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = {MongoDAOProperties.class})
class MongoDAOPropertiesTest {

    @Autowired
    MongoDAOProperties mongoDAOProperties;

    @Test
    void testMongoDAOProperties() {
        assertThat(mongoDAOProperties, is(notNullValue()));
        assertThat(mongoDAOProperties.getDatabase(), is(not(emptyOrNullString())));
    }

}