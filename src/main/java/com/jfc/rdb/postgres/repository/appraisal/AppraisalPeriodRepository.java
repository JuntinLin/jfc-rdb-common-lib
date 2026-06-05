package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.AppraisalPeriod;

@Repository
public interface AppraisalPeriodRepository extends JpaRepository<AppraisalPeriod, UUID> {

    List<AppraisalPeriod> findByPeriodTypeOrderByYearDescQuarterDesc(String periodType);

    List<AppraisalPeriod> findByStatusOrderByYearDescQuarterDesc(String status);

    List<AppraisalPeriod> findAllByOrderByYearDescQuarterDesc();

    AppraisalPeriod findByPeriodTypeAndYearAndQuarter(String periodType, Integer year, Integer quarter);
}
