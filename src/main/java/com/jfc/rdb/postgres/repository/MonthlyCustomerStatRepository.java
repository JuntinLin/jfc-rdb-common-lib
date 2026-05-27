package com.jfc.rdb.postgres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.MonthlyCustomerStat;
@Repository
public interface MonthlyCustomerStatRepository extends JpaRepository<MonthlyCustomerStat, Long> {
	// 依客戶編號查詢
    List<MonthlyCustomerStat> findByCustomerCode(String customerCode);

    // 依統計月份查詢
    List<MonthlyCustomerStat> findByStatMonth(String statMonth);

    // 依客戶編號和統計月份查詢（唯一）
    Optional<MonthlyCustomerStat> findByCustomerCodeAndStatMonth(String customerCode, String statMonth);

    // 依客戶編號查詢，按月份排序
    List<MonthlyCustomerStat> findByCustomerCodeOrderByStatMonthDesc(String customerCode);

    // 查詢某月份區間的資料
    @Query("SELECT m FROM MonthlyCustomerStat m WHERE m.statMonth BETWEEN :startMonth AND :endMonth ORDER BY m.statMonth")
    List<MonthlyCustomerStat> findByStatMonthBetween(@Param("startMonth") String startMonth,
                                                      @Param("endMonth") String endMonth);

    // 查詢某客戶某月份區間的資料
    @Query("SELECT m FROM MonthlyCustomerStat m WHERE m.customerCode = :customerCode AND m.statMonth BETWEEN :startMonth AND :endMonth ORDER BY m.statMonth")
    List<MonthlyCustomerStat> findByCustomerCodeAndStatMonthBetween(@Param("customerCode") String customerCode,
                                                                     @Param("startMonth") String startMonth,
                                                                     @Param("endMonth") String endMonth);

    // 檢查是否存在
    boolean existsByCustomerCodeAndStatMonth(String customerCode, String statMonth);

    // 刪除某客戶的所有資料
    void deleteByCustomerCode(String customerCode);

    // 刪除某月份的所有資料
    void deleteByStatMonth(String statMonth);
}
