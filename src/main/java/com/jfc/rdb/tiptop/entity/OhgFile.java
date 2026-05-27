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

// ========== 便利方法 (Convenience Methods) ==========
    
    /**
     * 取得客訴單號
     * 提供直接存取，避免每次都要透過 getId().getOhg01()
     */
    @Transient
    public String getOhg01() {
        return this.id != null ? this.id.getOhg01() : null;
    }
    
    /**
     * 取得類別
     * 提供直接存取，避免每次都要透過 getId().getOhg02()
     */
    @Transient
    public String getOhg02() {
        return this.id != null ? this.id.getOhg02() : null;
    }
    
    /**
     * 取得行序
     * 提供直接存取，避免每次都要透過 getId().getOhg03()
     */
    @Transient
    public Long getOhg03() {
        return this.id != null ? this.id.getOhg03() : null;
    }
    
    /**
     * 設定客訴單號（便利方法）
     */
    public void setOhg01(String ohg01) {
        if (this.id == null) {
            this.id = new OhgFilePK();
        }
        this.id.setOhg01(ohg01);
    }
    
    /**
     * 設定類別（便利方法）
     */
    public void setOhg02(String ohg02) {
        if (this.id == null) {
            this.id = new OhgFilePK();
        }
        this.id.setOhg02(ohg02);
    }
    
    /**
     * 設定行序（便利方法）
     */
    public void setOhg03(Long ohg03) {
        if (this.id == null) {
            this.id = new OhgFilePK();
        }
        this.id.setOhg03(ohg03);
    }
    
    /**
     * 取得處理類別的中文描述
     */
    @Transient
    public String getProcessTypeDescription() {
        String ohg02 = getOhg02();
        if (ohg02 == null) return "未知";
        
        return switch (ohg02) {
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
    public static OhgFile create(String ohg01, String ohg02, Long ohg03) {
        OhgFile ohgFile = new OhgFile();
        ohgFile.setId(new OhgFilePK(ohg01, ohg02, ohg03));
        return ohgFile;
    }
}
