package com.travelport.mongodbplayground.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
public class YearbookMap {
    private String uniqueId;
    private String school;
    private int year;
    Map<String, Student> people;

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return toJson(objectMapper);
    }

    public String toJson(ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }

    public void addStudent(Student newStudent) {
        log.info("result: {}", people.put(newStudent.getName(), newStudent));
    }

}
