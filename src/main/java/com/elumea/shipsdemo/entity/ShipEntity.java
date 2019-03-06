package com.elumea.shipsdemo.entity;

import javax.persistence.*;

@Entity
@Table(
    name = "ship",
    indexes = {@Index(columnList = "time_started"), @Index(columnList = "time_finished")})
public class ShipEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  private Integer imo;

  private String length;

  private Long port_id;

  // @JsonFormat  (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone =
  // "Europe/Berlin", locale = "en_GB")
  private String time_started;

  // @JsonFormat  (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone =
  // "Europe/Berlin", locale = "en_GB")
  private String time_finished;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getImo() {
    return imo;
  }

  public void setImo(Integer imo) {
    this.imo = imo;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public Long getPort_id() {
    return port_id;
  }

  public void setPort_id(Long port_id) {
    this.port_id = port_id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTime_started() {
    return time_started;
  }

  public void setTime_started(String time_started) {
    this.time_started = time_started;
  }

  public String getTime_finished() {
    return time_finished;
  }

  public void setTime_finished(String time_finished) {
    this.time_finished = time_finished;
  }
}
