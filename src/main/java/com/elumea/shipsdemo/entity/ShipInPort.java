package com.elumea.shipsdemo.entity;

public class ShipInPort extends ShipEntity {

  //  private String length;
  //  private Integer id;
  //  private String name;
  //  private Date timeStarted;
  //  private Integer imo;

  public ShipInPort() {}

  private Integer unique;
  private String minTime;
  private String maxTime;
  private String avgTime;
  private Integer minTime_imo;
  private Integer maxTime_imo;

  public Integer getUnique() {
    return unique;
  }

  public void setUnique(Integer unique) {
    this.unique = unique;
  }

  public String getMinTime() {
    return minTime;
  }

  public void setMinTime(String minTime) {
    this.minTime = minTime;
  }

  public String getMaxTime() {
    return maxTime;
  }

  public void setMaxTime(String maxTime) {
    this.maxTime = maxTime;
  }

  public String getAvgTime() {
    return avgTime;
  }

  public void setAvgTime(String avgTime) {
    this.avgTime = avgTime;
  }

  public Integer getMinTime_imo() {
    return minTime_imo;
  }

  public void setMinTime_imo(Integer minTime_imo) {
    this.minTime_imo = minTime_imo;
  }

  public Integer getMaxTime_imo() {
    return maxTime_imo;
  }

  public void setMaxTime_imo(Integer maxTime_imo) {
    this.maxTime_imo = maxTime_imo;
  }
}
