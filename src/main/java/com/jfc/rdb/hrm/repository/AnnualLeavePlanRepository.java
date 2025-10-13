package com.jfc.rdb.hrm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.TWALPlanInfo;
/**
 * 年假計劃資料
 */
@Repository
public interface AnnualLeavePlanRepository extends JpaRepository<TWALPlanInfo, UUID> {
	/**
     * 查找指定年度的年假計劃
     */
    @Query("SELECT alp FROM TWALPlanInfo alp WHERE alp.year = :year")
    List<TWALPlanInfo> findByYear(@Param("year") Integer year);
    
    /**
     * 查找指定日期範圍內的年假計劃
     */
    @Query("SELECT alp FROM TWALPlanInfo alp WHERE :startDate <= alp.endDate AND :endDate >= alp.beginDate")
    List<TWALPlanInfo> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    
    /**
     * 查找指定員工在指定日期範圍內的年假計劃
     */
    @Query("SELECT alp FROM TWALPlanInfo alp WHERE alp.employee.employeeCode = :employeeCode " +
           "AND :startDate <= alp.endDate AND :endDate >= alp.beginDate")
    List<TWALPlanInfo> findByEmployeeAndDateRange(
            @Param("employeeCode") UUID employeeCode,
            @Param("startDate") LocalDate startDate, 
            @Param("endDate") LocalDate endDate);
}
