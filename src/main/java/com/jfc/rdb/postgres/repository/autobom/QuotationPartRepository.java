package com.jfc.rdb.postgres.repository.autobom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfc.rdb.postgres.entity.autobom.QuotationPart;

public interface QuotationPartRepository extends JpaRepository<QuotationPart, Long> {
    List<QuotationPart> findByQuotationIdOrderById(Long quotationId);
}
