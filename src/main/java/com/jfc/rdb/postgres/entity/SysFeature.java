package com.jfc.rdb.postgres.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_feature")
public class SysFeature {

    @Id
    @Column(name = "feature_code", length = 50)
    private String featureCode;

    @Column(name = "feature_name", nullable = false, length = 100)
    private String featureName;

    @Column(name = "parent_code", length = 50)
    private String parentCode;

    @Column(name = "path", length = 200)
    private String path;

    @Column(name = "icon", length = 50)
    private String icon;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "keywords", length = 500)
    private String keywords;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Transient
    private List<SysFeature> children = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
