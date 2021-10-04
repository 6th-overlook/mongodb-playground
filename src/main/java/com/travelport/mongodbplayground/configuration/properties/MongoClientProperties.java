package com.travelport.mongodbplayground.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@EnableConfigurationProperties
@ConfigurationProperties("mongo-client-properties")
public class MongoClientProperties {
    private String connectionString;
}
