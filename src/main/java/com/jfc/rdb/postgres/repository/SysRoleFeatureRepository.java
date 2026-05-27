package com.jfc.rdb.postgres.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.SysRoleFeature;
import com.jfc.rdb.postgres.entity.SysRoleFeatureId;

@Repository
public interface SysRoleFeatureRepository extends JpaRepository<SysRoleFeature, SysRoleFeatureId> {

    List<SysRoleFeature> findByRoleCode(String roleCode);

    @Query("SELECT DISTINCT rf.featureCode FROM SysRoleFeature rf WHERE rf.roleCode IN :roleCodes")
    List<String> findFeatureCodesByRoleCodes(@Param("roleCodes") List<String> roleCodes);
}
