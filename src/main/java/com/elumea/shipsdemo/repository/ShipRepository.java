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
          "SELECT  CAST (id AS INTEGER) as id , name, CAST (imo AS INTEGER) as imo, length, time_started FROM ship s WHERE port_id = ?1  AND ?2 BETWEEN s.time_started AND s.time_finished",
      nativeQuery = true)
  List<Map<String, Object>> queryShipsInPortByDate(long port_id, String dateTime);

  @Query(
      value =
          "SELECT "
              + "        CAST (COUNT(DISTINCT imo) AS INTEGER)  AS unique, "
              + "        to_char(AVG(t1.duration),  'YYYY-MM-DD HH24:MI:SS') AS avgTime, "
              + "        to_char(MIN(t1.duration),  'YYYY-MM-DD HH24:MI:SS') AS minTime, "
              + "        to_char(MAX(t1.duration),  'YYYY-MM-DD HH24:MI:SS') AS maxTime, "
              + "        CAST ((array_agg(imo))[1] AS INTEGER) AS minTime_imo, "
              + "        CAST ((array_agg(imo))[array_upper((array_agg(imo)), 1)] AS INTEGER) AS maxTime_imo  "
              + "   FROM "
              + "        (SELECT   s.*, "
              + "            (TO_TIMESTAMP(s.time_finished, 'YYYY-MM-DD HH24:MI:SS') - TO_TIMESTAMP(s.time_started,  'YYYY-MM-DD HH24:MI:SS'))  AS duration  "
              + "        FROM  ship s  "
              + "        WHERE port_id = ?1 AND s.time_started BETWEEN ?2 AND ?3 ORDER BY  duration ASC) AS t1 ",
      nativeQuery = true)
  Map<String, Object> queryShipsInPortSummary(long port_id, String startTime, String endTime);
}
