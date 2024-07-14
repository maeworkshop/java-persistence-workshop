package com.maemresen.java.persistence.spring.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "circuits")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Circuit {

  @Id
  private Integer circuitId;

  private String circuitRef;
  private String name;
  private String location;
  private String country;
  private Float lat;
  private Float lng;
  private Integer alt;
  private String url;
}