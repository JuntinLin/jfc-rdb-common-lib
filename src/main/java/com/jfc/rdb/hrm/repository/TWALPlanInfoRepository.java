package com.jfc.rdb.hrm.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.TWALPlanInfo;

@Repository
public interface TWALPlanInfoRepository extends JpaRepository<TWALPlanInfo, UUID> {

	/**
     * Find all leave balances for a specific fiscal year
     */
	@Query("SELECT t FROM TWALPlanInfo t WHERE t.fiscalYear.fiscalYearId = :fiscalYearId")
	List<TWALPlanInfo> findByFiscalYearId(@Param("fiscalYearId") UUID fiscalYearId);
    
    /**
     * Find all leave balances for a specific employee
     */
    List<TWALPlanInfo> findByEmployeeEmployeeId(UUID employeeId);
    
    /**
     * Find all leave balances for a specific fiscal year and department
     */
    @Query("SELECT ali FROM TWALPlanInfo ali " +
           "JOIN ali.employee e " +
           "JOIN e.department d " +
           "WHERE ali.year = :year " +
           "AND d.departmentCode = :departmentCode")
    List<TWALPlanInfo> findByFiscalYearAndDepartment(
            @Param("year") Integer year, 
            @Param("departmentCode") String departmentCode);
    
    /**
     * 查找指定員工在指定日期範圍內的年假計劃
     */
    @Query("SELECT alpi FROM TWALPlanInfo alpi WHERE alpi.employee.employeeCode = :employeeCode " +
           "AND alpi.year = :year")
    List<TWALPlanInfo> findByEmployeeAndYear(
            @Param("employeeCode") String employeeCode,
            @Param("year") Integer year);
}
