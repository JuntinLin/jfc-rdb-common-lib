package com.jfc.rdb.postgres.repository.autobom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfc.rdb.postgres.entity.autobom.EcbRateMedian;

public interface EcbRateMedianRepository extends JpaRepository<EcbRateMedian, String> {

    Optional<EcbRateMedian> findByOpcode(String opcode);
}
