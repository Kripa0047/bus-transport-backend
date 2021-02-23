package com.example.springboot;

import java.lang.annotation.Documented;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "Location")
public class Location {

  @Id
  public String id;
  public String name;

  public Location() {}

  public Location(String name) {
    this.name = name;
  }

}