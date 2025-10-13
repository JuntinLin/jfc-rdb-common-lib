package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the BMA_FILE database table.
 * 
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BmaFilePK{
	private String bma01; //bma01	varchar2(40)	主件料件編號	主件料件編號 儲存該產品結構組合的主件料件編號。 主件料件編號需在料件基本資料主檔中。
	private String bma06; //bma06	varchar2(20)	特性代碼
}