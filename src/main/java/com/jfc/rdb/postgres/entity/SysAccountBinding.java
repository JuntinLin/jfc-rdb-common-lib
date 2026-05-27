package com.jfc.rdb.postgres.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_account_binding",
       uniqueConstraints = @UniqueConstraint(columnNames = {"provider_type", "provider_uid"}))
public class SysAccountBinding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bind_id")
    private Integer bindId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "provider_type", nullable = false, length = 20)
    private String providerType;

    @Column(name = "provider_uid", nullable = false, length = 100)
    private String providerUid;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
