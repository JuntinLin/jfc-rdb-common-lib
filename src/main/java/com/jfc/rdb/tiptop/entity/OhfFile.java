package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jfc.rdb.tiptop.model.enums.ComplaintProcessType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 
 * 客訴經手人員記錄檔(ohf_file)
 * */
@Entity
@Table(name = "OHF_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OhfFile implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private OhfFilePK id;
    
    @Column(name = "ohf03", length = 10)
    private String ohf03;    // 主辦人員
    
    @Column(name = "ohf04", length = 10)
    private String ohf04;    // 審核人員
    
    @Column(name = "ohf05", length = 10)
    private String ohf05;    // 責任單位
    
    @Column(name = "ohf06", length = 1)
    private String ohf06;    // No Use
    
    @Column(name = "ohf07", length = 1)
    private String ohf07;    // No Use

    @OneToMany(mappedBy = "ohfFile", cascade = CascadeType.ALL)
    private List<OhgFile> ohgFiles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ohf01", referencedColumnName = "ohc01", insertable = false, updatable = false)
    private OhcFile ohcFile;

    @Transient
    public ComplaintProcessType getProcessType() {
        return ComplaintProcessType.fromCode(this.id.getOhf02());
    }
}


