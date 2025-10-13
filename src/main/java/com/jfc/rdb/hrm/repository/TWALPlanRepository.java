package com.jfc.rdb.hrm.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.TWALPlan;

@Repository
public interface TWALPlanRepository extends JpaRepository<TWALPlan, UUID> {
	/**
     * 查找指定日期範圍內的年假計劃
     */
    @Query("SELECT alp FROM TWALPlan alp WHERE alp.year = :year")
    List<TWALPlan> findByYear(@Param("year") Integer year);
    
}
