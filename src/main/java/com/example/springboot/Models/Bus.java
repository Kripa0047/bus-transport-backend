package com.example.springboot;

import java.lang.annotation.Documented;
import java.util.*;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "Bus")
public class Bus {

  @Id
  public String id;
  public String busNumber;
  public String description;
  public List<Map<String, String>> stops;
  public List<String> days;

  public Bus() {}

  public Bus(String busNumber, String description, List<Map<String, String>> stops, List<String> days) {
    this.busNumber = busNumber;
    this.description = description;
    this.stops = stops;
    this.days = days;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setBusNumber(String busNumber) {
    this.busNumber = busNumber;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStops(List<Map<String,String>> stops) {
    this.stops = stops;
  }

  public void setDays(List<String> days) {
    this.days = days;
  }

}