package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.AppraisalScore;

@Repository
public interface AppraisalScoreRepository extends JpaRepository<AppraisalScore, UUID> {

    List<AppraisalScore> findByFormIdOrderByItemCodeAsc(UUID formId);

    void deleteByFormId(UUID formId);
}
