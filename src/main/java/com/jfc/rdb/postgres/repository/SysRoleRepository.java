package com.jfc.rdb.postgres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.SysRole;

@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, String> {
}
