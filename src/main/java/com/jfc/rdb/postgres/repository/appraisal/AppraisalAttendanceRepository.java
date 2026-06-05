package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.AppraisalAttendance;

@Repository
public interface AppraisalAttendanceRepository extends JpaRepository<AppraisalAttendance, UUID> {

    List<AppraisalAttendance> findByFormId(UUID formId);

    void deleteByFormId(UUID formId);
}
