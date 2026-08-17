package com.jfc.rdb.postgres.repository.autobom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfc.rdb.postgres.entity.autobom.QuotationProcessStep;

public interface QuotationProcessStepRepository extends JpaRepository<QuotationProcessStep, Long> {
    List<QuotationProcessStep> findByQuotationIdOrderByPartIdAscStepOrderAsc(Long quotationId);
}
