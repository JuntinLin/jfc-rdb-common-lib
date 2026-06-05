package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.DeptAppraisalForm;

@Repository
public interface DeptAppraisalFormRepository extends JpaRepository<DeptAppraisalForm, UUID> {

    List<DeptAppraisalForm> findByPeriodIdOrderByDeptCodeAsc(UUID periodId);

    Optional<DeptAppraisalForm> findByPeriodIdAndDeptCode(UUID periodId, String deptCode);
}
