package com.jfc.rdb.postgres.repository.autobom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jfc.rdb.postgres.entity.autobom.MaterialPrice;

public interface MaterialPriceRepository extends JpaRepository<MaterialPrice, Long> {

    /**
     * Tier1 牌號精準列查詢；Tier2 大類彙總列則傳 grade=""（見 entity 註解：空字串代表
     * 該 category 的彙總列，非 Tier1 精準牌號）。
     */
    Optional<MaterialPrice> findByCategoryAndGrade(String category, String grade);

    List<MaterialPrice> findByCategory(String category);

    @Transactional
    void deleteByCategory(String category);
}
