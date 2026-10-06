package com.jfc.rdb.postgres.repository.gps;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfc.rdb.postgres.entity.gps.GpsStationaryEvent;

public interface GpsStationaryEventRepository extends JpaRepository<GpsStationaryEvent, Long> {

    List<GpsStationaryEvent> findByStartTimeBetweenOrderByStartTimeDesc(LocalDateTime from, LocalDateTime to);

    List<GpsStationaryEvent> findByDeviceSerialNumberAndStartTimeBetweenOrderByStartTimeDesc(
            String deviceSerialNumber, LocalDateTime from, LocalDateTime to);
}
