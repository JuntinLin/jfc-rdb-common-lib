package com.jfc.rdb.postgres.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.SysFeatureField;

@Repository
public interface SysFeatureFieldRepository extends JpaRepository<SysFeatureField, Integer> {

    List<SysFeatureField> findByFeatureCodeIn(List<String> featureCodes);

    List<SysFeatureField> findByFeatureCode(String featureCode);
}
