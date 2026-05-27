package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//客訴單號單身檔(ohd_file)
@Entity
@Table(name = "OHD_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OhdFile implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private OhdFilePK id;
    
    @Column(name = "ohd03", length = 255)
    private String ohd03;    // 客訴內容說明
    
    @Column(name = "ohd04", length = 1)
    private String ohd04;    // No Use
    
    @Column(name = "ohd05", length = 1)
    private String ohd05;    // No Use
    
    @Column(name = "ohdplant", length = 10)
    private String ohdplant; // 所屬營運中心
    
    @Column(name = "ohdlegal", length = 10)
    private String ohdlegal; // 所屬法人

    @ManyToOne
    @JoinColumn(name = "ohd01", referencedColumnName = "ohc01", insertable = false, updatable = false)
    private OhcFile ohcFile;
}
