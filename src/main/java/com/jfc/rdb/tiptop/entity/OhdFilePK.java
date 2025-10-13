package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OhdFilePK implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "ohd01", length = 20)
    private String ohd01;    // 客訴單號
    
    @Column(name = "ohd02", precision = 5)
    private Long ohd02;      // 行序
}