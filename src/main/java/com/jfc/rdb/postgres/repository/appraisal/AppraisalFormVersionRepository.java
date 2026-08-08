package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.AppraisalFormVersion;

@Repository
public interface AppraisalFormVersionRepository extends JpaRepository<AppraisalFormVersion, UUID> {

    List<AppraisalFormVersion> findByFormIdOrderByVersionAsc(UUID formId);

    @Query("SELECT COALESCE(MAX(v.version), 0) FROM AppraisalFormVersion v WHERE v.formId = :formId")
    int findMaxVersionByFormId(@Param("formId") UUID formId);

    Optional<AppraisalFormVersion> findByFormIdAndVersion(UUID formId, Integer version);
}
