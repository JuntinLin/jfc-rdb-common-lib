package com.jfc.rdb.postgres.repository.autobom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfc.rdb.postgres.entity.autobom.Quotation;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    List<Quotation> findAllByOrderByCreatedAtDesc();
}
