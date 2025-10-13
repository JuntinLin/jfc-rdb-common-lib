package com.jfc.rdb.hrm.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
/**
 * Annual Leave Balance entity containing unused leave information
 */
@Data
@Entity
@Table(name = "AnnualLeaveBalance")
public class AnnualLeaveBalance {
	@Id
    private UUID AnnualLeaveBalanceId; //*AnnualLeaveBalanceId	年假結餘ID	Guid
    
    private UUID FiscalYearId; //FiscalYearId	財政年度ID(資料來自FiscalYear表的FiscalYearId欄位)	Guid
    
    @ManyToOne
    @JoinColumn(name = "EmployeeId") //EmployeeId	員工ID(資料來自Employee表的EmployeeId欄位)	Guid
    private Employee employee; 
    
    @Column(precision = 10, scale = 2)
    private BigDecimal TotalDays;        // Total vacation days allocated //PlanDays	本年可休天數	Decimal
    
    @Column(precision = 10, scale = 2)
    private BigDecimal UsedDays;         // Vacation days already used //ActualDays	本年已休天數	Decimal
    
    @Column(precision = 10, scale = 2)
    private BigDecimal RemainingDays;    // Remaining vacation days //RemainderDays	本年未休天數	Decimal
    
    @Column(precision = 10, scale = 2)
    private BigDecimal ExpiredDays;      // Expired vacation days
}
