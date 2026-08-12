package com.jfc.rdb.postgres.repository.pcn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.pcn.PcnRoleAssignee;

@Repository
public interface PcnRoleAssigneeRepository extends JpaRepository<PcnRoleAssignee, String> {
}
