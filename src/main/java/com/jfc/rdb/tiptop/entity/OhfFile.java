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
    
// ========== 便利方法 (Convenience Methods) ==========
    
    /**
     * 取得客訴單號
     * 提供直接存取，避免每次都要透過 getId().getOhf01()
     */
    @Transient
    public String getOhf01() {
        return this.id != null ? this.id.getOhf01() : null;
    }
    
    /**
     * 取得類別
     * 提供直接存取，避免每次都要透過 getId().getOhf02()
     */
    @Transient
    public String getOhf02() {
        return this.id != null ? this.id.getOhf02() : null;
    }
    
    /**
     * 設定客訴單號（便利方法）
     * 注意：這會建立新的 PK 物件或更新現有的
     */
    public void setOhf01(String ohf01) {
        if (this.id == null) {
            this.id = new OhfFilePK();
        }
        this.id.setOhf01(ohf01);
    }
    
    /**
     * 設定類別（便利方法）
     * 注意：這會建立新的 PK 物件或更新現有的
     */
    public void setOhf02(String ohf02) {
        if (this.id == null) {
            this.id = new OhfFilePK();
        }
        this.id.setOhf02(ohf02);
    }
    
    /**
     * 取得處理類別的中文描述
     */
    @Transient
    public String getProcessTypeDescription() {
        String ohf02 = getOhf02();
        if (ohf02 == null) return "未知";
        
        return switch (ohf02) {
            case "0" -> "客訴原因";
            case "1" -> "調查結果";
            case "2" -> "處理對策及改善對策";
            case "3" -> "審核";
            case "4" -> "核決";
            case "5" -> "結案註記";
            default -> "未知類別";
        };
    }
    
    /**
     * 建立新實例的輔助方法
     */
    public static OhfFile create(String ohf01, String ohf02) {
        OhfFile ohfFile = new OhfFile();
        ohfFile.setId(new OhfFilePK(ohf01, ohf02));
        return ohfFile;
    }
}


