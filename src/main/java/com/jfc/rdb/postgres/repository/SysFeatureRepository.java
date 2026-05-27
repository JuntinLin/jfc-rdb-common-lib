package com.jfc.rdb.postgres.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.SysFeature;

@Repository
public interface SysFeatureRepository extends JpaRepository<SysFeature, String> {

    List<SysFeature> findByEnabledTrueOrderBySortOrder();

    List<SysFeature> findByParentCodeIsNullAndEnabledTrueOrderBySortOrder();

    List<SysFeature> findByParentCodeAndEnabledTrueOrderBySortOrder(String parentCode);

    List<SysFeature> findByFeatureCodeInAndEnabledTrueOrderBySortOrder(List<String> featureCodes);
}
