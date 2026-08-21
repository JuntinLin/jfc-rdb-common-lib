package com.jfc.rdb.postgres.repository.pcn;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.pcn.OrderChangeNotified;

@Repository
public interface OrderChangeNotifiedRepository extends JpaRepository<OrderChangeNotified, Long> {

    Optional<OrderChangeNotified> findByOrderNoAndChangeSeq(String orderNo, BigDecimal changeSeq);

    boolean existsByOrderNoAndChangeSeq(String orderNo, BigDecimal changeSeq);

    List<OrderChangeNotified> findByStatus(String status);

    List<OrderChangeNotified> findByNotifiedAtBetween(LocalDateTime start, LocalDateTime end);
}
