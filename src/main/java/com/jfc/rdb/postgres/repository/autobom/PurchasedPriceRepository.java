package com.jfc.rdb.postgres.repository.autobom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jfc.rdb.postgres.entity.autobom.PurchasedPrice;

public interface PurchasedPriceRepository extends JpaRepository<PurchasedPrice, Long> {

    /**
     * Tier1 精準鍵查詢；Tier2 類別彙總列則傳 normalizedKey="" 查（見 entity 註解：
     * 空字串代表該 category 的彙總列，非 Tier1 精準鍵）。
     */
    Optional<PurchasedPrice> findByCategoryAndNormalizedKey(String category, String normalizedKey);

    List<PurchasedPrice> findByCategory(String category);

    @Transactional
    void deleteByCategory(String category);
}
