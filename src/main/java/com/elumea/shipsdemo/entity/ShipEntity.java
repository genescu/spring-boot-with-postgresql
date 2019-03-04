package com.elumea.shipsdemo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
    name = "ship",
    indexes = {@Index(columnList = "time_started"), @Index(columnList = "time_finished")})
public class ShipEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull private String name;

  @NotNull private Integer imo;

  @NotNull private String length;

  @NotNull private Long port_id;

  @NotNull private String time_started;

  @NotNull private String time_finished;

  public ShipEntity() {}

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
