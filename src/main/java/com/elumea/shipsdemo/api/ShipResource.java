package com.elumea.shipsdemo.api;

import com.elumea.shipsdemo.entity.ShipEntity;
import com.elumea.shipsdemo.exceptions.ResourceNotFoundException;
import com.elumea.shipsdemo.repository.ShipRepository;
import com.elumea.shipsdemo.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ShipResource {

  @Autowired private ShipRepository shipRepository;

  private ServiceUtils serviceUtils = ServiceUtils.getInstance();

  @GetMapping(value = "/ships")
  public Iterable<ShipEntity> retrieveAllShips() {
    return shipRepository.findAll();
  }

  @GetMapping(value = "/ships/{id}")
  public ShipEntity retrieveShip(@PathVariable long id) {
    Optional<ShipEntity> ship = shipRepository.findById(id);
    if (!ship.isPresent()) throw new ResourceNotFoundException(Long.toString(id));
    return ship.get();
  }

  @DeleteMapping(value = "/ships/{id}")
  public void deleteShip(@PathVariable long id) {
    shipRepository.deleteById(id);
  }

  @PostMapping(value = "/ships")
  public ResponseEntity<Object> createShip(@RequestBody ShipEntity ship) {
    ShipEntity savedShip = shipRepository.save(ship);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedShip.getId())
            .toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping(value = "/ships/{id}")
  public ResponseEntity<Object> updateShip(@RequestBody ShipEntity ship, @PathVariable long id) {
    Optional<ShipEntity> shipOptional = shipRepository.findById(id);
    if (!shipOptional.isPresent()) return ResponseEntity.notFound().build();
    ship.setId(id);
    shipRepository.save(ship);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/port/{id}/{dateTime}")
  public List<Map<Object, Integer>> shipsInPort(
      @PathVariable long id, @PathVariable String dateTime) {
    return shipRepository.findShipsInPortByDate(id, serviceUtils.formatToDate(dateTime));
  }

  @GetMapping(value = "/port/{id}/{startTime}/{endTime}")
  public Object shipsInPortSummary(
      @PathVariable long id, @PathVariable String startTime, @PathVariable String endTime) {
    return shipRepository.findShipsInPortSummary(
            id, serviceUtils.formatToDate(startTime), serviceUtils.formatToDate(endTime))
        .toArray()[0];
  }
}
