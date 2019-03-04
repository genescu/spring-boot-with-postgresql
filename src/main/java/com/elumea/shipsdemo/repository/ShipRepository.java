package com.elumea.shipsdemo.repository;

import com.elumea.shipsdemo.entity.ShipEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShipRepository extends CrudRepository<ShipEntity, Long> {
  @Query(
      value =
          "SELECT id, name, imo, length, time_started FROM ship s WHERE port_id = ?1  AND ?2 BETWEEN s.time_started AND s.time_finished",
      nativeQuery = true)
  List<Map<Object, Integer>> findShipsInPortByDate(long port_id, String dateTime);

  @Query(
      value =
          "SELECT  COUNT(DISTINCT imo) AS count_unique, "
              + "  to_char(avg(t1.duration), 'YYYY-MM-DD HH24:MI:SS') AS average_time, "
              + "  to_char(MIN(duration), 'YYYY-MM-DD HH24:MI:SS') AS minimum_time_duration, "
              + "  to_char(MAX(duration), 'YYYY-MM-DD HH24:MI:SS') AS maximum_time_duration, "
              + "  (array_agg(imo))[1] AS minimum_time_duration_imo, "
              + "  (array_agg(imo))[array_upper((array_agg(imo)), 1)] AS maximum_time_duration_imo "
              + "         "
              + "FROM "
              + "(SELECT  s.*, (TO_TIMESTAMP(s.time_finished,'YYYY-MM-DD HH24:MI:SS') - TO_TIMESTAMP(s.time_started,'YYYY-MM-DD HH24:MI:SS'))  AS duration "
              + "FROM ship s "
              + "WHERE port_id = ?1  "
              + "AND s.time_started BETWEEN ?2 AND ?3 "
              + "ORDER BY duration ASC) AS t1 ",
      nativeQuery = true)
  List<Map<Object, Integer>> findShipsInPortSummary(long port_id, String startTime, String endTime);
}
