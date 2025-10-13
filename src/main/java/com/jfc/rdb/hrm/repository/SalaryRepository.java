package com.jfc.rdb.hrm.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.hrm.entity.SalaryFixedDetail;
import com.jfc.rdb.hrm.model.dto.EmployeeWageDto;

/**
 * 薪資資料 
 */
@Repository
public interface SalaryRepository extends JpaRepository<SalaryFixedDetail, UUID> {
	/**
     * 獲取所有員工的時薪資料（原始查詢）
     * Native query to avoid JPQL validation and constructor issues
    
    @Query(value = "SELECT e.employee_id, e.employee_code, e.cn_name, " +
                  "(SELECT SUM(sfd.key_value)/240 " +
                  " FROM SalaryFixedDetail sfd " +
                  " WHERE sfd.employee_id = e.employee_id " +
                  "   AND sfd.key_value > 0 " +
                  "   AND sfd.end_date > GETDATE()) as hourly_wage, " +
                  "e.hire_date, " +
                  "alpi.remainder_amount " +
                  "FROM employee e " +
                  "LEFT JOIN TWALPlanInfo alpi ON alpi.employee_id = e.employee_id " +
                  "WHERE (SELECT SUM(sfd.key_value)/240 " +
                  "       FROM SalaryFixedDetail sfd " +
                  "       WHERE sfd.employee_id = e.employee_id " +
                  "         AND sfd.key_value > 0 " +
                  "         AND sfd.end_date > GETDATE()) > 0 " +
                  "  AND GETDATE() BETWEEN alpi.begin_date AND alpi.end_date " +
                  "ORDER BY e.employee_code", 
            nativeQuery = true)
     */
	@Query(value = "select e.employeeId, e.code, e.cnName, hw.hourlyWage, e.date, alpi.remainderAmount " +
					"from Employee e " +
					"left outer join (select sfd.employeeId, sum(sfd.keyValue)/240 as hourlyWage " +
					"from SalaryFixedDetail sfd " +
					"left outer join SalaryKey sk on sk.salaryKeyId = sfd.salaryKeyId " +
					"where sfd.keyValue > 0 and sfd.enddate > getdate() " +
					"group by sfd.employeeId) as hw on hw.employeeid = e.employeeId " +
					"left outer join TWALPlanInfo alpi on alpi.employeeId = e.employeeId " + 
					"where hourlyWage > 0 and  getdate() between alpi.beginDate and alpi.enddate " + 
					"order by code", 
			nativeQuery = true)
    List<Object[]> getEmployeeHourlyWagesRaw();
    
    /**
     * 封裝原始查詢，將結果轉換為 DTO
     */
    default List<EmployeeWageDto> getEmployeeHourlyWages() {
        List<Object[]> rawResults = getEmployeeHourlyWagesRaw();
        return rawResults.stream()
            .map(row -> {
                EmployeeWageDto dto = new EmployeeWageDto();
             // Handle UUID conversion
                if (row[0] != null) {
                    if (row[0] instanceof UUID) {
                        dto.setEmployeeId((UUID) row[0]);
                    } else {
                        dto.setEmployeeId(UUID.fromString(row[0].toString()));
                    }
                }
                dto.setEmployeeCode((String) row[1]);
                dto.setEmployeeName((String) row[2]);
                dto.setHourlyWage((java.math.BigDecimal) row[3]);
             // Convert Timestamp to LocalDate for the hire date
                if (row[4] != null) {
                    if (row[4] instanceof java.sql.Timestamp) {
                        dto.setHireDate(((java.sql.Timestamp) row[4]).toLocalDateTime().toLocalDate());
                    } else if (row[4] instanceof java.time.LocalDate) {
                        dto.setHireDate((java.time.LocalDate) row[4]);
                    } else {
                        // Fallback
                        dto.setHireDate(java.time.LocalDate.parse(row[4].toString()));
                    }
                }
                // Handle possible Number types for remainder_amount
                if (row[5] != null) {
                    if (row[5] instanceof Number) {
                        dto.setAnnualLeaveEntitlement(((Number) row[5]).intValue());
                    } else {
                        try {
                            dto.setAnnualLeaveEntitlement(Integer.parseInt(row[5].toString()));
                        } catch (NumberFormatException e) {
                            dto.setAnnualLeaveEntitlement(0);
                        }
                    }
                } else {
                    dto.setAnnualLeaveEntitlement(0);
                }
                return dto;
            })
            .collect(java.util.stream.Collectors.toList());
    }
}
