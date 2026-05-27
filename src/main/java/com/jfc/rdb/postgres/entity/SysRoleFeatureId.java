package com.jfc.rdb.postgres.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleFeatureId implements Serializable {
    private String roleCode;
    private String featureCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRoleFeatureId that = (SysRoleFeatureId) o;
        return Objects.equals(roleCode, that.roleCode) && Objects.equals(featureCode, that.featureCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleCode, featureCode);
    }
}
