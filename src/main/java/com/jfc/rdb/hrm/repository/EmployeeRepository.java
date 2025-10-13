package com.jfc.rdb.hrm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
	List<Employee> findByDepartmentDepartmentId(UUID departmentId);
    List<Employee> findByFlagIsTrue();
    
    /**
     * Find employees who are active as of the reference date:
     * - Hired on or before the reference date, AND
     * - Last work date is after the reference date OR last work date is null
     * 
     * @param referenceDate The reference date to determine active status
     * @return List of active employees as of the reference date
     */
    @Query("SELECT e FROM Employee e WHERE e.hireDate <= :referenceDate AND (e.lastWorkDate > :referenceDate OR e.lastWorkDate IS NULL) AND e.flag = true")
    List<Employee> findActiveEmployeesAsOf(@Param("referenceDate") LocalDate referenceDate);
    
    /**
     * Find employees who are currently active:
     * - Hired on or before today, AND
     * - Last work date is after today OR last work date is null
     * 
     * @return List of currently active employees
     */
    @Query("SELECT e FROM Employee e WHERE e.hireDate <= CURRENT_DATE AND (e.lastWorkDate > CURRENT_DATE OR e.lastWorkDate IS NULL) AND e.flag = true")
    List<Employee> findCurrentActiveEmployees();
}
