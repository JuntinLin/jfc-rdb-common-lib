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
	 * Get comprehensive attendance statistics for the specified date range This
	 * includes overtime, leave, abnormal attendance, and work hours
	 * 
	 * @param startDate     Start date of the period
	 * @param endDate       End date of the period
	 * @param employeeId    Optional employee ID filter
	 * @param departmentId  Optional department ID filter
	 * @param corporationId Optional corporation ID filter
	 * @return List of attendance statistics
	 * CodeInfo
	 * 'AttendanceKind_004' 請假, 'AttendanceKind_011' 特殊假
	 */
	@Query(value = """
			WITH ActiveEmployees AS (
			    -- 獲取查詢期間內的所有在職員工
			    SELECT DISTINCT
			        e.EmployeeId,
			        e.Code AS employeeCode,
			        e.CnName AS employeeName,
			        d.Code AS departmentCode,
			        d.ShortName AS departmentName,
			        c.CorporationId,
			        c.ShortName AS corporationName
			    FROM Employee e
			    INNER JOIN Department d ON e.DepartmentId = d.DepartmentId
			    INNER JOIN Corporation c ON e.CorporationId = c.CorporationId
			    WHERE e.Flag = 1
			        AND e.Date <= :endDate
			        AND (e.LastWorkDate IS NULL OR e.LastWorkDate >= :startDate)
			        AND (:employeeId IS NULL OR e.EmployeeId = :employeeId)
			        AND (:departmentId IS NULL OR e.DepartmentId = :departmentId)
			        AND (:corporationId IS NULL OR c.CorporationId = :corporationId)
			),
			OvertimeStats AS (
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

			        SELECT '加班-平日' AS col1, o.EmployeeId, o.Date, '分段一' AS name, SUM(o.WorkOTOne) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.WorkOTOne > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId
			        
			        UNION ALL

			        SELECT '加班-平日' AS col1, o.EmployeeId, o.Date, '分段二' AS name, SUM(o.WorkOTSec) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.WorkOTSec > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId
			        
			        UNION ALL

			        SELECT '加班-平日' AS col1, o.EmployeeId, o.Date, '分段三' AS name, SUM(o.WorkOTThree) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.WorkOTThree > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId
			        
			        UNION ALL

			        SELECT '加班-休息日' AS col1, o.EmployeeId, o.Date, '分段一' AS name, SUM(o.DayOffOTOne) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.DayOffOTOne > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId
			        
			        UNION ALL

			        SELECT '加班-休息日' AS col1, o.EmployeeId, o.Date, '分段二' AS name, SUM(o.DayOffOTSec) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.DayOffOTSec > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId
			        
			        UNION ALL

			        SELECT '加班-休息日' AS col1, o.EmployeeId, o.Date, '分段三' AS name, SUM(o.DayOffOTThree) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.DayOffOTThree > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId

			        UNION ALL

			        SELECT '加班-假日' AS col1, o.EmployeeId, o.Date, '分段一' AS name, SUM(o.HolidayOTOne) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.HolidayOTOne > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId

			        UNION ALL

			        SELECT '加班-假日' AS col1, o.EmployeeId, o.Date, '分段二' AS name, SUM(o.HolidayOTSec) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.HolidayOTSec > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId

			        UNION ALL

			        SELECT '加班-假日' AS col1, o.EmployeeId, o.Date, '分段三' AS name, SUM(o.HolidayOTThree) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.HolidayOTThree > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId

			        UNION ALL

			        SELECT '加班-節日' AS col1, o.EmployeeId, o.Date, '分段一' AS name, SUM(o.FestivalOTOne) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.FestivalOTOne > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId

			        UNION ALL

			        SELECT '加班-節日' AS col1, o.EmployeeId, o.Date, '分段二' AS name, SUM(o.FestivalOTSec) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.FestivalOTSec > 0
			            AND o.ApproveResultId = 'OperatorResult_001'
			            AND o.StateId = 'PlanState_003'
			            AND o.Date BETWEEN :startDate AND :endDate
			        GROUP BY o.EmployeeId, o.Date, o.AttendanceTypeId

			        UNION ALL

			        SELECT '加班-節日' AS col1, o.EmployeeId, o.Date, '分段三' AS name, SUM(o.FestivalOTThree) AS value, o.AttendanceTypeId
			        FROM AttendanceOTResult o
			        WHERE o.FestivalOTThree > 0
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
			    ),WorkHoursStats AS (
			     -- 應出勤工時
			     SELECT
			         '工時統計' AS col1,
			         roll.EmployeeId,
			         roll.Date,
			         '應出勤工時' AS name,
			         CASE WHEN ar.IsRestRank = '0' THEN ar.WorkHours ELSE 0 END AS value,
			         '' AS AttendanceTypeId
			     FROM AttendanceRollcall roll
			     INNER JOIN AttendanceRank ar ON roll.AttendanceRankId = ar.AttendanceRankId
			     WHERE roll.AttendanceTypeId >= 'R'
			         AND roll.Date BETWEEN :startDate AND :endDate

			     UNION ALL

			     -- 實際出勤工時
			     SELECT
			         '工時統計' AS col1,
			         call2.EmployeeId,
			         call2.Date,
			         '實際出勤工時' AS name,
			         ROUND(CAST(SUM(call2.Hours) / 60 AS DECIMAL(18,2)), 2) AS value,
			         t.AttendanceTypeId
			     FROM AttendanceRollcall call2
			     LEFT JOIN AttendanceType t ON call2.AttendanceTypeId = t.AttendanceTypeId
			     WHERE call2.Flag = 1
			         AND t.TypeTag = '1'
			         AND call2.Date BETWEEN :startDate AND :endDate
			     GROUP BY call2.EmployeeId, t.Name, call2.Date, t.AttendanceTypeId
			 ),
			    AllStats AS (
			        SELECT * FROM OvertimeStats WHERE value > 0
			        UNION ALL
			        SELECT * FROM LeaveStats WHERE value > 0
			        UNION ALL
			     SELECT * FROM WorkHoursStats WHERE value > 0
			    )
			    SELECT
			        s.col1,
			        ae.departmentCode,
			     	ae.employeeCode,
			     	ae.departmentName,
			     	ae.employeeName,
			        COALESCE(s.name, '無考勤記錄') AS itemName,
			        COALESCE(SUM(s.value), 0) AS value,
			     	ae.corporationName,
			        CONCAT(CONVERT(VARCHAR(10), :startDate, 111), ' 至 ', CONVERT(VARCHAR(10), :endDate, 111)) AS period,
			        GETDATE() AS date,
			        '' AS shift
			    FROM ActiveEmployees ae
			    INNER JOIN AllStats s ON ae.EmployeeId = s.EmployeeId
			    GROUP BY s.col1, ae.departmentCode, ae.employeeCode, ae.departmentName, ae.employeeName, s.name, ae.corporationName
			 	ORDER BY ae.employeeCode, s.col1, s.name
			    """, nativeQuery = true)
	List<Object[]> findAttendanceStatistics(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("employeeId") UUID employeeId, @Param("departmentId") UUID departmentId,
			@Param("corporationId") UUID corporationId);

	/**
	 * Get overtime statistics by employee for the specified date range
	 * 
	 * @param startDate  Start date of the period
	 * @param endDate    End date of the period
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
			    AND (:departmentId IS NULL OR e.DepartmentId = :departmentId)
			    AND (:corporationId IS NULL OR e.CorporationId = :corporationId)
			""", nativeQuery = true)
	List<Object[]> findOvertimeStatistics(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("employeeId") UUID employeeId, @Param("departmentId") UUID departmentId,
			@Param("corporationId") UUID corporationId);

	/**
	 * Get leave statistics by employee for the specified date range
	 * 
	 * @param startDate  Start date of the period
	 * @param endDate    End date of the period
	 * @param employeeId Optional employee ID filter
	 * @return List of leave statistics
	 * 
	 * 'AttendanceKind_004' 各類假別, 'AttendanceKind_011' 特殊假, 'AttendanceKind_007' 出差
	 * AttendanceUnit_003 分鐘
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
			    AND (:departmentId IS NULL OR e.DepartmentId = :departmentId)
			    AND (:corporationId IS NULL OR e.CorporationId = :corporationId)
			GROUP BY d.Code, e.Code, d.ShortName, e.CnName, t.Name, c.ShortName, r.Date
			""", nativeQuery = true)
	List<Object[]> findLeaveStatistics(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("employeeId") UUID employeeId, @Param("departmentId") UUID departmentId,
			@Param("corporationId") UUID corporationId);

	/**
	 * Get abnormal attendance statistics (late, early leave, absence)
	 * 
	 * @param startDate  Start date of the period
	 * @param endDate    End date of the period
	 * @param employeeId Optional employee ID filter
	 * @return List of abnormal attendance statistics
	 * 'AttendanceKind_001' 遲到, 'AttendanceKind_002' 早退, 'AttendanceKind_003' 曠工
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
			    AND (:departmentId IS NULL OR e.DepartmentId = :departmentId)
			    AND (:corporationId IS NULL OR e.CorporationId = :corporationId)
			GROUP BY d.Code, e.Code, d.ShortName, e.CnName, t.Name, c.ShortName, r.Date
			""", nativeQuery = true)
	List<Object[]> findAbnormalAttendanceStatistics(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("employeeId") UUID employeeId, 
            @Param("departmentId") UUID departmentId,
			@Param("corporationId") UUID corporationId);

	/**
	 * Get work hours statistics
	 * 
	 * @param startDate  Start date of the period
	 * @param endDate    End date of the period
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
			    AND (:departmentId IS NULL OR e.DepartmentId = :departmentId)
			    AND (:corporationId IS NULL OR e.CorporationId = :corporationId)
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
			    AND (:departmentId IS NULL OR e.DepartmentId = :departmentId)
			    AND (:corporationId IS NULL OR e.CorporationId = :corporationId)
			GROUP BY d.Code, e.Code, d.ShortName, e.CnName, c.ShortName, r.Date
			""", nativeQuery = true)
	List<Object[]> findWorkHoursStatistics(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("employeeId") UUID employeeId, @Param("departmentId") UUID departmentId,
			@Param("corporationId") UUID corporationId);
	
	/**
	 * Get missed punch statistics (單次未刷卡)
	 * 
	 * @param startDate     Start date of the period
	 * @param endDate       End date of the period
	 * @param employeeId    Optional employee ID filter
	 * @param departmentId  Optional department ID filter
	 * @param corporationId Optional corporation ID filter
	 * @return List of missed punch statistics
	 */
	@Query(value = """
			SELECT
			    '單次未刷卡' AS col1,
			    d.Code AS departmentCode,
			    e.Code AS employeeCode,
			    d.ShortName AS departmentName,
			    e.CnName AS employeeName,
			    t.Name AS itemName,
			    COUNT(rd.AttendanceRollcallDetailId) AS value,
			    c.ShortName AS corporationName,
			    CONCAT(CONVERT(VARCHAR(10), :startDate, 111), ' 至 ', CONVERT(VARCHAR(10), :endDate, 111)) AS period,
			    r.Date AS date,
			    '' AS shift
			FROM AttendanceRollcall r
			INNER JOIN Employee e ON r.EmployeeId = e.EmployeeId
			INNER JOIN Department d ON e.DepartmentId = d.DepartmentId
			INNER JOIN Corporation c ON e.CorporationId = c.CorporationId
			INNER JOIN AttendanceRollcallDetail rd ON r.AttendanceRollcallId = rd.AttendanceRollcallId
			INNER JOIN AttendanceType t ON rd.AttendanceTypeId = t.AttendanceTypeId
			WHERE r.AttendanceTypeId >= 'R'
			    AND t.AttendanceKindId = 'AttendanceKind_008'
			    AND rd.AttendanceTypeId = '907'
			    AND r.Date BETWEEN :startDate AND :endDate
			    AND (:employeeId IS NULL OR e.EmployeeId = :employeeId)
			    AND (:departmentId IS NULL OR e.DepartmentId = :departmentId)
			    AND (:corporationId IS NULL OR e.CorporationId = :corporationId)
			GROUP BY d.Code, e.Code, d.ShortName, e.CnName, t.Name, c.ShortName, r.Date
			""", nativeQuery = true)
	List<Object[]> findMissedPunchStatistics(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("employeeId") UUID employeeId, @Param("departmentId") UUID departmentId,
			@Param("corporationId") UUID corporationId);
	/**
	 * Get punch details for an employee within a date range
	 * 
	 * @param startDate  Start date of the period
	 * @param endDate    End date of the period
	 * @param employeeId Employee ID
	 * @return List of punch details
	 */
	/**
	 * 出勤率統計：按員工匯總應出勤時數與實際出勤時數
	 *
	 * row[0] departmentCode, row[1] employeeCode, row[2] departmentName,
	 * row[3] employeeName, row[4] corporationName,
	 * row[5] requiredHours, row[6] actualHours
	 */
	@Query(value = """
			SELECT
			    d.Code AS departmentCode,
			    e.Code AS employeeCode,
			    d.ShortName AS departmentName,
			    e.CnName AS employeeName,
			    c.ShortName AS corporationName,
			    COALESCE(req.requiredHours, 0) AS requiredHours,
			    COALESCE(act.actualHours, 0) + COALESCE(ot.overtimeHours, 0) AS actualHours,
			    pd.ShortName AS parentDepartmentName
			FROM Employee e
			INNER JOIN Department d ON e.DepartmentId = d.DepartmentId
			LEFT JOIN Department pd ON pd.DepartmentId = d.ParentId
			INNER JOIN Corporation c ON e.CorporationId = c.CorporationId
			LEFT JOIN (
			    SELECT r.EmployeeId,
			           SUM(CASE WHEN ar.IsRestRank = '0' THEN ar.WorkHours ELSE 0 END) AS requiredHours
			    FROM AttendanceRollcall r
			    INNER JOIN AttendanceRank ar ON r.AttendanceRankId = ar.AttendanceRankId
			    WHERE r.AttendanceTypeId >= 'R'
			      AND r.Date BETWEEN :startDate AND :endDate
			    GROUP BY r.EmployeeId
			) req ON req.EmployeeId = e.EmployeeId
			LEFT JOIN (
			    SELECT r2.EmployeeId,
			           ROUND(CAST(SUM(r2.Hours) / 60 AS DECIMAL(18,2)), 2) AS actualHours
			    FROM AttendanceRollcall r2
			    INNER JOIN AttendanceType t ON r2.AttendanceTypeId = t.AttendanceTypeId
			    WHERE r2.Flag = 1
			      AND t.TypeTag = '1'
			      AND r2.Date BETWEEN :startDate AND :endDate
			    GROUP BY r2.EmployeeId
			) act ON act.EmployeeId = e.EmployeeId
			LEFT JOIN (
			    SELECT o.EmployeeId,
			           ROUND(CAST(SUM(
			               COALESCE(o.WorkOTOne, 0) + COALESCE(o.WorkOTSec, 0) + COALESCE(o.WorkOTThree, 0) +
			               COALESCE(o.DayOffOTOne, 0) + COALESCE(o.DayOffOTSec, 0) + COALESCE(o.DayOffOTThree, 0) +
			               COALESCE(o.HolidayOTOne, 0) + COALESCE(o.HolidayOTSec, 0) + COALESCE(o.HolidayOTThree, 0) +
			               COALESCE(o.FestivalOTOne, 0) + COALESCE(o.FestivalOTSec, 0) + COALESCE(o.FestivalOTThree, 0)
			           ) AS DECIMAL(18,2)), 2) AS overtimeHours
			    FROM AttendanceOTResult o
			    WHERE o.ApproveResultId = 'OperatorResult_001'
			      AND o.StateId = 'PlanState_003'
			      AND o.Date BETWEEN :startDate AND :endDate
			    GROUP BY o.EmployeeId
			) ot ON ot.EmployeeId = e.EmployeeId
			WHERE e.Flag = 1
			    AND e.Date <= :endDate
			    AND (e.LastWorkDate IS NULL OR e.LastWorkDate >= :startDate)
			    AND (req.requiredHours IS NOT NULL OR act.actualHours IS NOT NULL OR ot.overtimeHours IS NOT NULL)
			    AND (:employeeId IS NULL OR e.EmployeeId = :employeeId)
			    AND (:departmentId IS NULL OR e.DepartmentId = :departmentId)
			    AND (:corporationId IS NULL OR e.CorporationId = :corporationId)
			ORDER BY d.Code, e.Code
			""", nativeQuery = true)
	List<Object[]> findAttendanceRate(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("employeeId") UUID employeeId, @Param("departmentId") UUID departmentId,
			@Param("corporationId") UUID corporationId);

	@Query(value = """
			SELECT
			    c.AttendanceRollcallCollectId,
			    c.EmployeeId,
			    e.Code AS employeeCode,
			    e.CnName AS employeeName,
			    c.Date AS punchTime,
			    m.Name AS machineName,
			    m.Place AS machineLocation,
			    c.IsManual,
			    '' AS remark
			FROM AttendanceRollcallCollect c
			INNER JOIN AttendanceRollcallDetail rd ON rd.AttendanceRollcallId = c.AttendanceRollcallId
			INNER JOIN Employee e ON c.EmployeeId = e.EmployeeId
			LEFT JOIN Machine m ON c.MachineId = m.MachineId
			WHERE c.EmployeeId = :employeeId
			    AND CAST(c.Date AS DATE) BETWEEN :startDate AND :endDate
			    AND rd.AttendanceTypeId = :attendanceTypeId
			ORDER BY c.Date ASC
			""", nativeQuery = true)
	List<Object[]> findPunchDetails(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("employeeId") UUID employeeId,
			@Param("attendanceTypeId") String attendanceTypeId);

}
