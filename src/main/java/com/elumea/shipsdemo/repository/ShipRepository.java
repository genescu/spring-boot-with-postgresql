package com.elumea.shipsdemo.repository;

import com.elumea.shipsdemo.entity.ShipEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends CrudRepository<ShipEntity, Long> {
  @Query(
      value =
          "SELECT id, name, imo, length, time_started FROM ship s WHERE port_id = ?1  AND ?2 BETWEEN s.time_started AND s.time_finished",
      nativeQuery = true)
  List<Map<Object, Integer>> findShipsInPortByDate(long port_id, String dateTime);

  @Query(
      value =
          "SELECT  COUNT (DISTINCT imo) as count, to_char(AVG (TO_TIMESTAMP(s.time_finished,'YYYY-MM-DD HH24:MI:SS') - TO_TIMESTAMP(s.time_started,'YYYY-MM-DD HH24:MI:SS') ), 'YYYY-MM-DD HH24:MI:SS') as avg_time FROM ship s "
              + "WHERE port_id = ?1 AND time_started BETWEEN ?2 AND ?3  ",
      nativeQuery = true)
  List<Map<Object, Integer>> findShipsInPortSummary(long port_id, String startTime, String endTime);
}
