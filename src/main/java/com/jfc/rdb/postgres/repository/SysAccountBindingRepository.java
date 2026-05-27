package com.jfc.rdb.postgres.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.SysAccountBinding;

@Repository
public interface SysAccountBindingRepository extends JpaRepository<SysAccountBinding, Integer> {

    List<SysAccountBinding> findByUserId(UUID userId);

    Optional<SysAccountBinding> findByProviderTypeAndProviderUid(String providerType, String providerUid);

    boolean existsByProviderTypeAndProviderUid(String providerType, String providerUid);
}
