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
public class OhgFilePK implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "ohg01", length = 20)
    private String ohg01;    // 客訴單號
    
    @Column(name = "ohg02", length = 1)
    private String ohg02;    // 類別

    @Column(name = "ohg03", precision = 5)
    private Long ohg03;      // 行序
}


