package com.jfc.rdb.postgres.repository.appraisal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.AppraisalSupervisorOverride;

@Repository
public interface AppraisalSupervisorOverrideRepository extends JpaRepository<AppraisalSupervisorOverride, String> {
}
