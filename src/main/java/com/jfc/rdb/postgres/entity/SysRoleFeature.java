package com.jfc.rdb.postgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_role_feature")
@IdClass(SysRoleFeatureId.class)
public class SysRoleFeature {

    @Id
    @Column(name = "role_code", length = 50)
    private String roleCode;

    @Id
    @Column(name = "feature_code", length = 50)
    private String featureCode;
}
