package com.jfc.rdb.postgres.repository.calendar;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfc.rdb.postgres.entity.calendar.AdHocHoliday;

public interface AdHocHolidayRepository extends JpaRepository<AdHocHoliday, LocalDate> {

    List<AdHocHoliday> findAllByOrderByHolidayDateDesc();

    /** 區間 [from, toExclusive) 內生效中的天災停班日期 */
    @Query("SELECT a.holidayDate FROM AdHocHoliday a " +
           "WHERE a.active = true AND a.holidayDate >= :from AND a.holidayDate < :toExclusive")
    List<LocalDate> findActiveDatesBetween(@Param("from") LocalDate from, @Param("toExclusive") LocalDate toExclusive);
}
