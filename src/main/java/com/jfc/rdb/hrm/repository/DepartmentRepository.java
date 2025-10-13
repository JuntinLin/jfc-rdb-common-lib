package com.jfc.rdb.hrm.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
	List<Department> findByOrderByDepartmentCode();
}
