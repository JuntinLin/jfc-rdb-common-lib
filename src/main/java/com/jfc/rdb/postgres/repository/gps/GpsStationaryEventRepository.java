package com.jfc.rdb.postgres.repository.gps;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfc.rdb.postgres.entity.gps.GpsStationaryEvent;

public interface GpsStationaryEventRepository extends JpaRepository<GpsStationaryEvent, Long> {

    List<GpsStationaryEvent> findByStartTimeBetweenOrderByStartTimeDesc(LocalDateTime from, LocalDateTime to);

    List<GpsStationaryEvent> findByDeviceSerialNumberAndStartTimeBetweenOrderByStartTimeDesc(
            String deviceSerialNumber, LocalDateTime from, LocalDateTime to);

    /** 查詢某車目前「進行中」尚未定案的停留事件，供後端重啟後接續計時用。 */
    Optional<GpsStationaryEvent> findFirstByDeviceSerialNumberAndOngoingTrueOrderByStartTimeDesc(String deviceSerialNumber);
}
