package com.jfc.rdb.hrm.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfc.rdb.hrm.entity.AttendanceCalendar;

public interface AttendanceCalendarRepository extends JpaRepository<AttendanceCalendar, UUID> {

    /**
     * 計算指定期間的工作天數
     * code = '101' 為工作日，flag = true 為有效資料
     */
    @Query("""
        SELECT COUNT(ac)
        FROM AttendanceCalendar ac
        INNER JOIN ac.attendanceHolidayType aht
        WHERE aht.code = '101'
          AND ac.date >= :startDate AND ac.date < :endDate
          AND ac.flag = true
    """)
    long countWorkingDays(@Param("startDate") LocalDateTime startDate,
                          @Param("endDate") LocalDateTime endDate);

    /**
     * 查詢指定期間的工作日明細
     */
    @Query("""
        SELECT ac
        FROM AttendanceCalendar ac
        INNER JOIN FETCH ac.attendanceHolidayType aht
        WHERE aht.code = '101'
          AND ac.date >= :startDate AND ac.date < :endDate
          AND ac.flag = true
        ORDER BY ac.date
    """)
    List<AttendanceCalendar> findWorkingDays(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    /**
     * 只取日期，不 hydrate 整個 entity——AttendanceCalendar 部分歷史資料的 GUID 欄位
     * (corporationId/attendanceSpellId/employeeId/fiscalYearId) 格式不合法，SELECT ac 整包
     * entity 會噴 "guid length must be 16"；只選 ac.date 這個 SQL Server DATETIME 欄位可繞開。
     */
    @Query("""
        SELECT ac.date
        FROM AttendanceCalendar ac
        INNER JOIN ac.attendanceHolidayType aht
        WHERE aht.code = '101'
          AND ac.date >= :startDate AND ac.date < :endDate
          AND ac.flag = true
    """)
    List<LocalDateTime> findWorkingDates(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    /**
     * 計算指定期間的總日曆天數
     */
    @Query("""
        SELECT COUNT(ac)
        FROM AttendanceCalendar ac
        INNER JOIN ac.attendanceHolidayType aht
        WHERE ac.date >= :startDate AND ac.date < :endDate
          AND ac.flag = true
    """)
    long countCalendarDays(@Param("startDate") LocalDateTime startDate,
                           @Param("endDate") LocalDateTime endDate);

    /**
     * 計算指定期間的非工作天數（103節日 + 104假日 + 105休息日）
     * 101工作日
     */
    @Query("""
        SELECT COUNT(ac)
        FROM AttendanceCalendar ac
        INNER JOIN ac.attendanceHolidayType aht
        WHERE aht.code IN ('103', '104', '105')
          AND ac.date >= :startDate AND ac.date < :endDate
          AND ac.flag = true
    """)
    long countNonWorkingDays(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate);

}
