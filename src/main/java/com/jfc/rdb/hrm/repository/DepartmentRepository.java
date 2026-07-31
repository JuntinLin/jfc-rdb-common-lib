package com.jfc.rdb.hrm.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
	List<Department> findByOrderByDepartmentCode();
	
	/**
     * 根據部門編碼查詢
     */
    Optional<Department> findByDepartmentCode(String departmentCode);
    
    /**
     * 根據部門名稱查詢
     */
    Optional<Department> findByName(String name);
    
    /**
     * 查詢所有有效部門
     */
    List<Department> findByFlagIsTrue();
    
    /**
     * 根據部門名稱模糊查詢
     */
    List<Department> findByNameContaining(String keyword);
    
    /**
     * 查詢指定上級部門的所有子部門
     */
    List<Department> findByParentIdAndFlagIsTrue(String parentId);
    
    /**
     * 查詢業務部及其所有子部門（使用遞迴CTE）
     * 
     * @return 業務部門層級結構
     */
    @Query(value = """
        WITH SalesDeptHierarchy AS (
            -- 找出業務部（根節點）
            SELECT DepartmentId, Code, ShortName, Name, [Describe], ParentId, 
                   Type, Flag, DeptLevel, OrderNumber, IsERP, CreateDate, LastModifiedDate,
                   0 AS HierarchyLevel
            FROM Department
            WHERE Name LIKE N'%業務部%' AND Flag = 1
            
            UNION ALL
            
            -- 遞迴找出所有子部門
            SELECT d.DepartmentId, d.Code, d.ShortName, d.Name, d.[Describe], d.ParentId,
                   d.Type, d.Flag, d.DeptLevel, d.OrderNumber, d.IsERP, d.CreateDate, d.LastModifiedDate,
                   h.HierarchyLevel + 1
            FROM Department d
            INNER JOIN SalesDeptHierarchy h ON d.ParentId = CAST(h.DepartmentId AS NVARCHAR(50))
            WHERE d.Flag = 1
        )
        SELECT DepartmentId, Code, ShortName, Name, [Describe], ParentId,
               Type, Flag, DeptLevel, OrderNumber, IsERP, CreateDate, LastModifiedDate
        FROM SalesDeptHierarchy
        ORDER BY HierarchyLevel, OrderNumber, Code
        """, nativeQuery = true)
    List<Department> findSalesDepartmentHierarchy();
    
    /**
     * 根據部門名稱關鍵字查詢該部門及其所有子部門
     * 
     * @param keyword 部門名稱關鍵字
     * @return 部門及子部門列表
     */
    @Query(value = """
        WITH DeptHierarchy AS (
            SELECT DepartmentId, Code, ShortName, Name, [Describe], ParentId,
                   Type, Flag, DeptLevel, OrderNumber, IsERP, CreateDate, LastModifiedDate,
                   0 AS HierarchyLevel
            FROM Department
            WHERE Name LIKE CONCAT(N'%', :keyword, N'%') AND Flag = 1
            
            UNION ALL
            
            SELECT d.DepartmentId, d.Code, d.ShortName, d.Name, d.[Describe], d.ParentId,
                   d.Type, d.Flag, d.DeptLevel, d.OrderNumber, d.IsERP, d.CreateDate, d.LastModifiedDate,
                   h.HierarchyLevel + 1
            FROM Department d
            INNER JOIN DeptHierarchy h ON d.ParentId = CAST(h.DepartmentId AS NVARCHAR(50))
            WHERE d.Flag = 1
        )
        SELECT DepartmentId, Code, ShortName, Name, [Describe], ParentId,
               Type, Flag, DeptLevel, OrderNumber, IsERP, CreateDate, LastModifiedDate
        FROM DeptHierarchy
        ORDER BY HierarchyLevel, OrderNumber, Code
        """, nativeQuery = true)
    List<Department> findDepartmentHierarchyByKeyword(@Param("keyword") String keyword);
    
    /**
     * 查詢指定層級的部門
     * 
     * @param deptLevel 部門層級
     * @return 該層級的部門列表
     */
    List<Department> findByDeptLevelAndFlagIsTrueOrderByOrderNumber(Integer deptLevel);

    /**
     * 查詢指定員工是否為任何部門負責人（Department.Principal = employeeId）
     */
    @Query(value = "SELECT COUNT(*) FROM Department WHERE Principal = :employeeId", nativeQuery = true)
    int countByPrincipal(@Param("employeeId") java.util.UUID employeeId);
}
