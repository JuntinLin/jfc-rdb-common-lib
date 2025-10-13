package com.jfc.rdb.hrm.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.Employee;
@Repository
public interface AttendanceStatisticsRepository extends JpaRepository<Employee, UUID> {
	/**
     * Get comprehensive attendance statistics for the specified date range
     * This includes overtime, leave, abnormal attendance, and work hours
     * 
     * @param startDate Start date of the period
     * @param endDate End date of the period
     * @param employeeId Optional employee ID filter
     * @param departmentId Optional department ID filter
     * @param corporationId Optional corporation ID filter
     * @return List of attendance statistics
     */
    @Query(value = """
        WITH OvertimeStats AS (
            SELECT 
                '加班-假日' AS col1,
                o.EmployeeId,
                o.Date,
                '_免稅' AS name,
                SUM(o.HolidayDutyFreeBase) AS value,
                o.AttendanceTypeId
            FROM AttendanceOTResult o
            WHERE o.HolidayDutyFreeBase > 0
                AND o.ApproveResultId = 'OperatorResult_001'
                AND o.StateId = 'PlanState_003'
                AND o.Date BETWEEN :startDate AND :endDate
            GROUP BY o.AttendanceTypeId, o.EmployeeId, o.Date
            
            UNION ALL
            
            SELECT 
                '加班-節日' AS col1,
                o.EmployeeId,
                o.Date,
                '_免稅' AS name,
                SUM(o.FestivalDutyFreeBase) AS value,
                o.AttendanceTypeId
            FROM AttendanceOTResult o
            WHERE o.FestivalDutyFreeBase > 0
                AND o.ApproveResultId = 'OperatorResult_001'
                AND o.StateId = 'PlanState_003'
                AND o.Date BETWEEN :startDate AND :endDate
            GROUP BY o.AttendanceTypeId, o.EmployeeId, o.Date
            
            UNION ALL
            
            SELECT 
                '加班-平日' AS col1,
                o.EmployeeId,
                o.Date,
                '分段一' AS name,
                SUM(o.WorkOTOne) AS value,
                o.AttendanceTypeId
            FROM AttendanceOTResult o
            WHERE o.WorkOTOne > 0
                AND o.ApproveResultId = 'OperatorResult_001'
                AND o.StateId = 'PlanState_003'
                AND o.Date BETWEEN :startDate AND :endDate
            GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId
        ),
        LeaveStats AS (
            SELECT 
                '請假(時)' AS col1,
                r.EmployeeId,
                r.Date,
                t.Name,
                CAST(ROUND(SUM(
                    CASE WHEN t.AttendanceKindId IN ('AttendanceKind_004', 'AttendanceKind_011') THEN 
                        CASE d.QuartersHoursUnit
                            WHEN 'AttendanceUnit_003' THEN d.Hours / 60 
                            ELSE d.Hours 
                        END
                    ELSE d.Hours
                    END
                ), 1) AS DECIMAL(10,1)) AS value,
                t.AttendanceTypeId
            FROM AttendanceRollcall r
            LEFT JOIN AttendanceRollcallDetail d ON r.AttendanceRollcallId = d.AttendanceRollcallId
            LEFT JOIN AttendanceType t ON d.AttendanceTypeId = t.AttendanceTypeId
            WHERE r.AttendanceTypeId >= 'R'
                AND t.AttendanceKindId IN ('AttendanceKind_004', 'AttendanceKind_011')
                AND r.Date BETWEEN :startDate AND :endDate
            GROUP BY r.EmployeeId, t.Name, r.Date, t.AttendanceTypeId
        ),
        AllStats AS (
            SELECT * FROM OvertimeStats
            UNION ALL
            SELECT * FROM LeaveStats
        )
        SELECT 
            s.col1,
            d.Code AS departmentCode,
            e.Code AS employeeCode,
            d.ShortName AS departmentName,
            e.CnName AS employeeName,
            s.name AS itemName,
            SUM(s.value) AS value,
            c.ShortName AS corporationName,
            CONCAT(CONVERT(VARCHAR(10), :startDate, 111), ' 至 ', CONVERT(VARCHAR(10), :endDate, 111)) AS period,
            GETDATE() AS date,
            '' AS shift
        FROM Employee e
        LEFT JOIN Department d ON d.DepartmentId = e.DepartmentId
        LEFT JOIN Corporation c ON e.CorporationId = c.CorporationId
        LEFT JOIN AllStats s ON e.EmployeeId = s.EmployeeId
        WHERE COALESCE(s.value, 0) <> 0
            AND (:employeeId IS NULL OR e.EmployeeId = :employeeId)
            AND (:departmentId IS NULL OR e.DepartmentId = :departmentId)
            AND (:corporationId IS NULL OR c.CorporationId = :corporationId)
        GROUP BY s.col1, d.Code, e.Code, d.ShortName, e.CnName, s.name, c.ShortName
        ORDER BY 1, 2, 3, 4, 5, 6
        """, nativeQuery = true)
    List<Object[]> findAttendanceStatistics(@Param("startDate") Date startDate, 
                                           @Param("endDate") Date endDate,
                                           @Param("employeeId") UUID employeeId,
                                           @Param("departmentId") UUID departmentId,
                                           @Param("corporationId") UUID corporationId);
    
    /**
     * Get overtime statistics by employee for the specified date range
     * 
     * @param startDate Start date of the period
     * @param endDate End date of the period
     * @param employeeId Optional employee ID filter
     * @return List of overtime statistics
     */
    @Query(value = """
        SELECT 
            '加班統計' AS col1,
            d.Code AS departmentCode,
            e.Code AS employeeCode,
            d.ShortName AS departmentName,
            e.CnName AS employeeName,
            CASE 
                WHEN o.WorkOTOne > 0 THEN '平日加班-分段一'
                WHEN o.HolidayOTOne > 0 THEN '假日加班-分段一'
                WHEN o.FestivalOTOne > 0 THEN '節日加班-分段一'
                WHEN o.WorkOTSec > 0 THEN '平日加班-分段二'
                WHEN o.HolidayOTSec > 0 THEN '假日加班-分段二'
                WHEN o.FestivalOTSec > 0 THEN '節日加班-分段二'
                WHEN o.WorkOTThree > 0 THEN '平日加班-分段三'
                WHEN o.HolidayOTThree > 0 THEN '假日加班-分段三'
                WHEN o.FestivalOTThree > 0 THEN '節日加班-分段三'
                ELSE '其他加班'
            END AS itemName,
            COALESCE(o.WorkOTOne, 0) + COALESCE(o.HolidayOTOne, 0) + 
            COALESCE(o.FestivalOTOne, 0) + COALESCE(o.WorkOTSec, 0) + 
            COALESCE(o.HolidayOTSec, 0) + COALESCE(o.FestivalOTSec, 0) +
            COALESCE(o.WorkOTThree, 0) + COALESCE(o.HolidayOTThree, 0) + 
            COALESCE(o.FestivalOTThree, 0) AS value,
            c.ShortName AS corporationName,
            CONCAT(CONVERT(VARCHAR(10), :startDate, 111), ' 至 ', CONVERT(VARCHAR(10), :endDate, 111)) AS period,
            o.Date AS date,
            '' AS shift
        FROM AttendanceOTResult o
        INNER JOIN Employee e ON o.EmployeeId = e.EmployeeId
        INNER JOIN Department d ON e.DepartmentId = d.DepartmentId
        INNER JOIN Corporation c ON e.CorporationId = c.CorporationId
        WHERE o.ApproveResultId = 'OperatorResult_001'
            AND o.StateId = 'PlanState_003'
            AND o.Date BETWEEN :startDate AND :endDate
            AND (:employeeId IS NULL OR e.EmployeeId = :employeeId)
        """, nativeQuery = true)
    List<Object[]> findOvertimeStatistics(@Param("startDate") Date startDate,
                                         @Param("endDate") Date endDate,
                                         @Param("employeeId") UUID employeeId);
    
    /**
     * Get leave statistics by employee for the specified date range
     * 
     * @param startDate Start date of the period
     * @param endDate End date of the period
     * @param employeeId Optional employee ID filter
     * @return List of leave statistics
     */
    @Query(value = """
        SELECT 
            '請假統計' AS col1,
            d.Code AS departmentCode,
            e.Code AS employeeCode,
            d.ShortName AS departmentName,
            e.CnName AS employeeName,
            t.Name AS itemName,
            CAST(ROUND(SUM(
                CASE WHEN t.AttendanceKindId IN ('AttendanceKind_004', 'AttendanceKind_011') THEN 
                    CASE rd.QuartersHoursUnit
                        WHEN 'AttendanceUnit_003' THEN rd.Hours / 60 
                        ELSE rd.Hours 
                    END
                ELSE rd.Hours
                END
            ), 1) AS DECIMAL(10,1)) AS value,
            c.ShortName AS corporationName,
            CONCAT(CONVERT(VARCHAR(10), :startDate, 111), ' 至 ', CONVERT(VARCHAR(10), :endDate, 111)) AS period,
            r.Date AS date,
            '' AS shift
        FROM AttendanceRollcall r
        INNER JOIN Employee e ON r.EmployeeId = e.EmployeeId
        INNER JOIN Department d ON e.DepartmentId = d.DepartmentId
        INNER JOIN Corporation c ON e.CorporationId = c.CorporationId
        LEFT JOIN AttendanceRollcallDetail rd ON r.AttendanceRollcallId = rd.AttendanceRollcallId
        LEFT JOIN AttendanceType t ON rd.AttendanceTypeId = t.AttendanceTypeId
        WHERE r.AttendanceTypeId >= 'R'
            AND t.AttendanceKindId IN ('AttendanceKind_004', 'AttendanceKind_011', 'AttendanceKind_007')
            AND r.Date BETWEEN :startDate AND :endDate
            AND (:employeeId IS NULL OR e.EmployeeId = :employeeId)
        GROUP BY d.Code, e.Code, d.ShortName, e.CnName, t.Name, c.ShortName, r.Date
        """, nativeQuery = true)
    List<Object[]> findLeaveStatistics(@Param("startDate") Date startDate,
                                      @Param("endDate") Date endDate,
                                      @Param("employeeId") UUID employeeId);
    
    /**
     * Get abnormal attendance statistics (late, early leave, absence)
     * 
     * @param startDate Start date of the period
     * @param endDate End date of the period
     * @param employeeId Optional employee ID filter
     * @return List of abnormal attendance statistics
     */
    @Query(value = """
        SELECT 
            '異常考勤' AS col1,
            d.Code AS departmentCode,
            e.Code AS employeeCode,
            d.ShortName AS departmentName,
            e.CnName AS employeeName,
            t.Name AS itemName,
            COUNT(r.AttendanceRollcallId) AS value,
            c.ShortName AS corporationName,
            CONCAT(CONVERT(VARCHAR(10), :startDate, 111), ' 至 ', CONVERT(VARCHAR(10), :endDate, 111)) AS period,
            r.Date AS date,
            '' AS shift
        FROM AttendanceRollcall r
        INNER JOIN Employee e ON r.EmployeeId = e.EmployeeId
        INNER JOIN Department d ON e.DepartmentId = d.DepartmentId
        INNER JOIN Corporation c ON e.CorporationId = c.CorporationId
        LEFT JOIN AttendanceRollcallDetail rd ON r.AttendanceRollcallId = rd.AttendanceRollcallId
        LEFT JOIN AttendanceType t ON rd.AttendanceTypeId = t.AttendanceTypeId
        WHERE r.AttendanceTypeId >= 'R'
            AND t.AttendanceKindId IN ('AttendanceKind_001', 'AttendanceKind_002', 'AttendanceKind_003')
            AND r.Date BETWEEN :startDate AND :endDate
            AND (:employeeId IS NULL OR e.EmployeeId = :employeeId)
        GROUP BY d.Code, e.Code, d.ShortName, e.CnName, t.Name, c.ShortName, r.Date
        """, nativeQuery = true)
    List<Object[]> findAbnormalAttendanceStatistics(@Param("startDate") Date startDate,
                                                   @Param("endDate") Date endDate,
                                                   @Param("employeeId") UUID employeeId);
    
    /**
     * Get work hours statistics
     * 
     * @param startDate Start date of the period
     * @param endDate End date of the period
     * @param employeeId Optional employee ID filter
     * @return List of work hours statistics
     */
    @Query(value = """
        SELECT 
            '工時統計' AS col1,
            d.Code AS departmentCode,
            e.Code AS employeeCode,
            d.ShortName AS departmentName,
            e.CnName AS employeeName,
            '應出勤工時' AS itemName,
            SUM(CASE WHEN ar.IsRestRank = '0' THEN ar.WorkHours ELSE 0 END) AS value,
            c.ShortName AS corporationName,
            CONCAT(CONVERT(VARCHAR(10), :startDate, 111), ' 至 ', CONVERT(VARCHAR(10), :endDate, 111)) AS period,
            r.Date AS date,
            '' AS shift
        FROM AttendanceRollcall r
        INNER JOIN Employee e ON r.EmployeeId = e.EmployeeId
        INNER JOIN Department d ON e.DepartmentId = d.DepartmentId
        INNER JOIN Corporation c ON e.CorporationId = c.CorporationId
        INNER JOIN AttendanceRank ar ON r.AttendanceRankId = ar.AttendanceRankId
        WHERE r.AttendanceTypeId >= 'R'
            AND r.Date BETWEEN :startDate AND :endDate
            AND (:employeeId IS NULL OR e.EmployeeId = :employeeId)
        GROUP BY d.Code, e.Code, d.ShortName, e.CnName, c.ShortName, r.Date
        
        UNION ALL
        
        SELECT 
            '工時統計' AS col1,
            d.Code AS departmentCode,
            e.Code AS employeeCode,
            d.ShortName AS departmentName,
            e.CnName AS employeeName,
            '實際出勤工時' AS itemName,
            ROUND(CAST(SUM(r.Hours) / 60 AS DECIMAL(18,2)), 2) AS value,
            c.ShortName AS corporationName,
            CONCAT(CONVERT(VARCHAR(10), :startDate, 111), ' 至 ', CONVERT(VARCHAR(10), :endDate, 111)) AS period,
            r.Date AS date,
            '' AS shift
        FROM AttendanceRollcall r
        INNER JOIN Employee e ON r.EmployeeId = e.EmployeeId
        INNER JOIN Department d ON e.DepartmentId = d.DepartmentId
        INNER JOIN Corporation c ON e.CorporationId = c.CorporationId
        LEFT JOIN AttendanceType t ON r.AttendanceTypeId = t.AttendanceTypeId
        WHERE r.Flag = 1 
            AND t.TypeTag = '1'
            AND r.Date BETWEEN :startDate AND :endDate
            AND (:employeeId IS NULL OR e.EmployeeId = :employeeId)
        GROUP BY d.Code, e.Code, d.ShortName, e.CnName, c.ShortName, r.Date
        """, nativeQuery = true)
    List<Object[]> findWorkHoursStatistics(@Param("startDate") Date startDate,
                                          @Param("endDate") Date endDate,
                                          @Param("employeeId") UUID employeeId);
}
