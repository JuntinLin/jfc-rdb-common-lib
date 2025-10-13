package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgFilePK  implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private String img01; //	varchar2(40)	料件編號	料件編號 儲存存放地點的料件編號
    private String img02; //	varchar2(10)	倉庫編號	倉庫編號 儲存料件所在之倉庫編號
    private String img03; //	varchar2(10)	儲位	儲位 儲存料件所在之儲位
    private String img04; //	varchar2(24)	批號	批號(進貨) 儲存料件所在之進貨批號
   
}
