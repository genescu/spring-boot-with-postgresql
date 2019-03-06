package com.elumea.shipsdemo.controller;

import com.elumea.shipsdemo.entity.ShipInPort;
import com.elumea.shipsdemo.repository.ShipRepository;
import com.elumea.shipsdemo.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ShipController {

  @Autowired private ShipRepository shipRepository;

  private ServiceUtils serviceUtils = ServiceUtils.getInstance();

  public ShipInPort findShipsInPortSummary(long port_id, String startTime, String endTime) {

    Map<String, Object> obj = shipRepository.queryShipsInPortSummary(port_id, startTime, endTime);
    ShipInPort shipInPort = new ShipInPort();

    shipInPort.setUnique((Integer) obj.get("unique"));
    shipInPort.setAvgTime((String) obj.get("avgTime"));

    shipInPort.setMaxTime((String) obj.get("maxTime"));
    shipInPort.setMaxTime_imo((Integer) obj.get("maxTime_imo"));

    shipInPort.setMinTime((String) obj.get("minTime"));
    shipInPort.setMinTime_imo((Integer) obj.get("minTime_imo"));

    return shipInPort;
  }

  public List<ShipInPort> findShipsInPortByDate(long port_id, String dateTime) {
    List<Map<String, Object>> objList = shipRepository.queryShipsInPortByDate(port_id, dateTime);
    ShipInPort shipInPort;

    List<ShipInPort> listShip = new ArrayList<>();

    for (int i = 0; i < objList.size(); i++) {

      shipInPort = new ShipInPort();
      shipInPort.setId((Integer) objList.get(i).get("id"));
      shipInPort.setLength((String) objList.get(i).get("length"));
      shipInPort.setName((String) objList.get(i).get("name"));
      shipInPort.setTime_started((String) objList.get(i).get("time_started"));
      shipInPort.setImo((Integer) objList.get(i).get("imo"));

      listShip.add(shipInPort);
    }

    return listShip;
  }
}
