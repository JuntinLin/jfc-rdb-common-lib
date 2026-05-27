package com.jfc.rdb.postgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_feature_field",
       uniqueConstraints = @UniqueConstraint(columnNames = {"feature_code", "field_name"}))
public class SysFeatureField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "feature_code", nullable = false, length = 50)
    private String featureCode;

    @Column(name = "field_name", nullable = false, length = 50)
    private String fieldName;

    @Column(name = "field_label", length = 50)
    private String fieldLabel;

    @Column(name = "secret_level", nullable = false)
    private Integer secretLevel = 1;
}
