package com.travelport.mongodbplayground.configuration.factory;

    import com.mongodb.ConnectionString;
    import com.mongodb.MongoClientSettings;
    import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.travelport.mongodbplayground.configuration.properties.MongoClientProperties;
import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Profile;

@Configuration
public class MongoClientFactory {

    @Bean
    @Profile("!test")
    public MongoClient createMongoClient(MongoClientProperties mongoClientProperties) {
        ConnectionString connectionString = new ConnectionString(mongoClientProperties.getConnectionString());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(settings);
    }

}
