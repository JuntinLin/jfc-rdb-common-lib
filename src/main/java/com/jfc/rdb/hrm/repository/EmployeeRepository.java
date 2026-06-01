package com.jfc.rdb.hrm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
	List<Employee> findByDepartmentDepartmentId(UUID departmentId);
    List<Employee> findByFlagIsTrue();
    Optional<Employee> findByEmployeeCode(String employeeCode);

    /**
     * 查詢所有員工（含離職），用於考勤統計查詢離職員工歷史資料
     */
    @Query("SELECT e FROM Employee e ORDER BY e.employeeCode")
    List<Employee> findAllEmployees();
    
    /**
     * Find employees who are active as of the reference date:
     * - Hired on or before the reference date, AND
     * - Last work date is after the reference date OR last work date is null
     * 
     * @param referenceDate The reference date to determine active status
     * @return List of active employees as of the reference date
     */
    @Query("SELECT e FROM Employee e WHERE e.hireDate <= :referenceDate AND (e.lastWorkDate > :referenceDate OR e.lastWorkDate IS NULL) AND e.flag = true ORDER BY e.employeeCode")
    List<Employee> findActiveEmployeesAsOf(@Param("referenceDate") LocalDate referenceDate);
    
    /**
     * Find employees who are currently active:
     * - Hired on or before today, AND
     * - Last work date is after today OR last work date is null
     * 
     * @return List of currently active employees
     */
    @Query("SELECT e FROM Employee e WHERE e.hireDate <= CURRENT_DATE AND (e.lastWorkDate > CURRENT_DATE OR e.lastWorkDate IS NULL) AND e.flag = true ORDER BY e.employeeCode")
    List<Employee> findCurrentActiveEmployees();
    
    /**
     * 查詢所有業務部門的員工（包含所有子部門）
     * 業務部下有：內銷課、外銷課、估價課、系統課等
     * 內銷課下有：南區組、台中辦公室等組別
     * 
     * 使用遞迴 CTE 查詢業務部及其所有子部門的員工
     * 
     * @return 所有業務部門的在職員工列表
     */
    @Query(value = """
        WITH SalesDeptHierarchy AS (
            -- 找出業務部（根節點）
            SELECT DepartmentId, Code, Name, ParentId, 0 AS Level
            FROM Department
            WHERE Name LIKE N'%業務部%' AND Flag = 1
            
            UNION ALL
            
            -- 遞迴找出所有子部門
            SELECT d.DepartmentId, d.Code, d.Name, d.ParentId, h.Level + 1
            FROM Department d
            INNER JOIN SalesDeptHierarchy h ON d.ParentId = CAST(h.DepartmentId AS NVARCHAR(50))
            WHERE d.Flag = 1
        )
        SELECT e.*
        FROM Employee e
        LEFT JOIN SalesDeptHierarchy s ON e.DepartmentId = s.DepartmentId
        WHERE (
    	  s.DepartmentId IS NOT NULL
          AND	e.Flag = 1
          AND e.Date <= GETDATE()
          AND (e.LastWorkDate > GETDATE() OR e.LastWorkDate IS NULL)
          ) or (e.Code = 'T0066')
        ORDER BY e.Code
        """, nativeQuery = true)
    List<Employee> findAllSalespeople();
    
    /**
     * 根據部門名稱關鍵字查詢該部門及其子部門的所有員工
     * 
     * @param departmentNameKeyword 部門名稱關鍵字（如：業務部、內銷課）
     * @return 該部門及子部門的在職員工列表
     */
    @Query(value = """
        WITH DeptHierarchy AS (
            -- 找出符合關鍵字的部門（根節點）
            SELECT DepartmentId, Code, Name, ParentId, 0 AS Level
            FROM Department
            WHERE Name LIKE CONCAT(N'%', :keyword, N'%') AND Flag = 1
            
            UNION ALL
            
            -- 遞迴找出所有子部門
            SELECT d.DepartmentId, d.Code, d.Name, d.ParentId, h.Level + 1
            FROM Department d
            INNER JOIN DeptHierarchy h ON d.ParentId = CAST(h.DepartmentId AS NVARCHAR(50))
            WHERE d.Flag = 1
        )
        SELECT e.*
        FROM Employee e
        INNER JOIN DeptHierarchy dh ON e.DepartmentId = dh.DepartmentId
        WHERE e.Flag = 1
          AND e.Date <= GETDATE()
          AND (e.LastWorkDate > GETDATE() OR e.LastWorkDate IS NULL)
        ORDER BY e.Code
        """, nativeQuery = true)
    List<Employee> findEmployeesByDepartmentKeyword(@Param("keyword") String departmentNameKeyword);
    
    /**
     * 查詢特定課別的員工（如：內銷課、外銷課）
     * 包含該課別下的所有組別
     * 
     * @param sectionName 課別名稱（如：內銷課、外銷課、估價課、系統課）
     * @return 該課別及其子組別的在職員工列表
     */
    @Query(value = """
        WITH SectionHierarchy AS (
            -- 找出指定課別
            SELECT DepartmentId, Code, Name, ParentId, 0 AS Level
            FROM Department
            WHERE Name = :sectionName AND Flag = 1
            
            UNION ALL
            
            -- 遞迴找出所有子組別
            SELECT d.DepartmentId, d.Code, d.Name, d.ParentId, h.Level + 1
            FROM Department d
            INNER JOIN SectionHierarchy h ON d.ParentId = CAST(h.DepartmentId AS NVARCHAR(50))
            WHERE d.Flag = 1
        )
        SELECT e.*
        FROM Employee e
        INNER JOIN SectionHierarchy s ON e.DepartmentId = s.DepartmentId
        WHERE e.Flag = 1
          AND e.Date <= GETDATE()
          AND (e.LastWorkDate > GETDATE() OR e.LastWorkDate IS NULL)
        ORDER BY e.Code
        """, nativeQuery = true)
    List<Employee> findEmployeesBySection(@Param("sectionName") String sectionName);
    
    /**
     * 查詢所有業務部門的員工（簡化版，不使用遞迴）
     * 直接透過部門名稱模糊匹配
     * 適用於部門結構較扁平或不需要嚴格層級關係的情況
     * 
     * @return 所有業務相關部門的在職員工列表
     */
    @Query("SELECT e FROM Employee e " +
           "WHERE e.department.name LIKE '%業務%' " +
           "OR e.department.name LIKE '%內銷%' " +
           "OR e.department.name LIKE '%外銷%' " +
           "OR e.department.name LIKE '%估價%' " +
           "OR e.department.shortName LIKE '%業務%' " +
           "AND e.flag = true " +
           "AND e.hireDate <= CURRENT_DATE " +
           "AND (e.lastWorkDate > CURRENT_DATE OR e.lastWorkDate IS NULL) " +
           "ORDER BY e.employeeCode")
    List<Employee> findAllSalespeopleSimple();
}
