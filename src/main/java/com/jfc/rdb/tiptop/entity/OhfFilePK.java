package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OhfFilePK implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "ohf01", length = 20)
    private String ohf01;    // 客訴單號
    
    @Column(name = "ohf02", length = 1)
    private String ohf02;    // 類別:0.客訴原因1.調查結果2.處理對策及改善對策3.審核4.核決5.結案註記
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OhfFilePK)) return false;
        OhfFilePK that = (OhfFilePK) o;
        return Objects.equals(ohf01, that.ohf01) &&
               Objects.equals(ohf02, that.ohf02);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ohf01, ohf02);
    }

}
