package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;

import com.jfc.rdb.tiptop.model.enums.ComplaintProcessType;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 客訴單處理說明檔(ohg_file)
 * */
@Entity
@Table(name = "OHG_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OhgFile implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private OhgFilePK id;
    
    @Column(name = "ohg04", length = 255)
    private String ohg04;    // 說明
    
    @Column(name = "ohg05", length = 1)
    private String ohg05;    // No Use
    
    @Column(name = "ohg06", length = 1)
    private String ohg06;    // No Use
    
    @Column(name = "ohgplant", length = 10)
    private String ohgplant; // 所屬營運中心
    
    @Column(name = "ohglegal", length = 10)
    private String ohglegal; // 所屬法人

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ohg01", referencedColumnName = "ohf01", insertable = false, updatable = false),
        @JoinColumn(name = "ohg02", referencedColumnName = "ohf02", insertable = false, updatable = false)
    })
    private OhfFile ohfFile;

    @Transient
    public ComplaintProcessType getProcessType() {
        return ComplaintProcessType.fromCode(this.id.getOhg02());
    }

}
