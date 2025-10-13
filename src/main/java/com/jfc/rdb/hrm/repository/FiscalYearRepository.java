package com.jfc.rdb.hrm.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.FiscalYear;
@Repository
public interface FiscalYearRepository extends JpaRepository<FiscalYear, UUID> {
	FiscalYear findByFlag(boolean flag);
}
