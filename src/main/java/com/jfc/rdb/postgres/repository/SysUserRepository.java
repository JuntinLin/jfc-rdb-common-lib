package com.jfc.rdb.postgres.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.SysUser;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, UUID> {

    Optional<SysUser> findByUsername(String username);

    Optional<SysUser> findByEmpNo(String empNo);

    boolean existsByUsername(String username);
}
