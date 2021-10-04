package com.travelport.mongodbplayground.utilties;

import com.travelport.mongodbplayground.model.Student;
import com.travelport.mongodbplayground.model.Yearbook;
import com.travelport.mongodbplayground.model.YearbookMap;

import java.util.*;

public class YearbookUtils {

    public static Yearbook getYearbook() {
        var john = new Student("John", 15, List.of("skiing", "hiking", "running"));
        var jane = new Student("Jane", 16, List.of("art", "music"));
        var ben = new Student("Ben", 18, List.of("biking", "hiking", "skiing", "Philosophy"));
        return new Yearbook(UUID.randomUUID().toString(), "In High", 2010, new ArrayList<>(List.of(john, jane, ben)));
    }

    public static YearbookMap getYearbookMap() {
            Map map = new HashMap();
            var john = new Student("John", 15, List.of("skiing", "hiking", "running"));
            var jane = new Student("Jane", 16, List.of("art", "music"));
            var ben = new Student("Ben", 18, List.of("biking", "hiking", "skiing", "Philosophy"));
            map.put(john.getName(), john );
            map.put(jane.getName(), jane );
            map.put(ben.getName(), ben );
            return new YearbookMap(UUID.randomUUID().toString(), "In High", 2010, map);
    }


}
