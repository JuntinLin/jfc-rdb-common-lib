--訂單 出貨 發票
--/*訂單統計*/
--String occ04; //	varchar2(10)	負責業務員編號
--select oeb01, oeb03, oea03, substr(oea01, 1, 4) as ordertype, substr(oeb04, 1, 1) as prdtype, oea14, oea24 * oeb14 as amtnotax, oea24 * oeb14t as amthastax
select oea03, occ02, sum(oea24 * oeb14)
from oea_file
left outer join oeb_file on oeb01 = oea01
left outer join occ_file on occ01 = oea03
        --oea03	varchar2(10)	帳款客戶編號	"帳款客戶編號 occ01 MISC: 雜項客戶, 可輸入簡稱,統一編號"*/
        --20180309 胡董指示:未確認的訂單需一併統計
where oeaconf in ('Y', 'N') and not(oeb04 is null)
	and  to_char(oea02, 'YYYY-MM-DD') between '2025-09-01' and '2025-09-31'
	and occ04 = 'T0005'
group by oea03, occ02	 
;

--銷貨統計
--/*
        //oga01	varchar2(20)	出貨單號/出通單號
        //oga03	varchar2(10)	帳款客戶編號	帳款客戶編號 occ01   MISC: 雜項客戶, 可輸入簡稱,統一編號
        //ogb04	varchar2(40)	產品編號	產品編號 ima01(img01)
        //oga02	date	出貨日期
        //ogb31	varchar2(20)	訂單單號	訂單單號 oea01(oeb01)
        //oea14	varchar2(10)	人員編號	人員編號   gen01
        //ogb31	varchar2(20)	訂單單號	訂單單號 oea01(oeb01)
        //ogb32	number(5)	訂單項次	訂單項次       oeb03
        //ogb01	varchar2(20)	出貨單號	出貨單號 oga01
        //ogb03	number(5)	項次
        */
--銷貨統計    
--select ogb01, ogb03, oga03, substr(oga01, 1, 4) as ordertype, substr(ogb04, 1, 1) as prdtype, oea14, oga24 * ogb14 as amtnotax, oga24 * ogb14t as amthastax
select oga03, occ02,  sum(oga24 * ogb14)
from oga_file
left outer join ogb_file on ogb01 = oga01
left outer join occ_file on occ01 = oga03
left outer join oea_file on oea01 = ogb31  --ogb31	varchar2(20)	訂單單號	訂單單號 oea01(oeb01)
where ogaconf = 'Y' and ogapost='Y' -- 已確認 and 已過帳
  	and oga09='2'--出貨單
 	and  to_char(oga02, 'YYYY-MM-DD') between '2025-09-01' and '2025-09-31'
     and occ04 = 'T0005'
group by oga03, occ02;

--/*應收帳款單身檔(omb_file)
    oma00	varchar2(2)	帳款類別	帳款類別 (1*:應收帳款, 2*:待抵帳款 3*:其他) 10.待抵帳扣費??(流通) 11.訂金應收, 12.出貨應收, 13.尾款應收, 14.雜項應收, 15.訂金/押金(流通),  16.代退款應收, 17.應收(流通), 18.儲值卡訂金(流通), 19.代收, 21.退貨折讓待抵, 22.雜項待抵, 23.預收(訂金),24.暫收(溢收), 25.折扣, 26.預收(流通), 27.代收應返, 28.代退, 31.其他應收(不列入應收帳款)
    omaconf	varchar2(1)	確認否	確認否 (Y/N)
    omavoid	varchar2(1)	作廢否	作廢否 (Y/N)
    omb15	number(20,6)	本幣單價
    omb16	number(20,6)	本幣未稅金額
    omb16t	number(20,6)	本幣含稅金額
    omb04	varchar2(40)	產品編號	產品編號 ima01/ogb04/ohb04
    
    ima06	varchar2(10)	分群碼	分群碼0: 表由其他相關系統而來尚未分群當在料件基本資料建立時, 可利用分群碼來預設 相關欄位基本預設值
    ima10	varchar2(10)	其他分群碼 二	其他分群碼 二提供給使用者, 對料件分群/分類除在分群碼中定義外, 可提供給其它的定義方式, 以供 管理/匯集報表 使用將可分成 一/二/三/四 四個欄位以供交互定義使用使用者可自行定義預設值 依分群碼類別預設 或 空白
    */

--select oma03, occ02, omb01, omb31, omb32, omb04, ima06, ima10, omb16, omb16t, omb12, ogb01, ogb03, oeb01, oeb03
--select oma03, occ02, sum(omb16) as amtnotax
select sum(omb16) as amtnotax
from omb_file
left outer join oma_file on oma01= omb01
left outer join occ_file on occ01 = oma03
left outer join ima_file on ima01 = omb04
left outer join ogb_file on ogb01 = omb31 and ogb03 = omb32
left outer join oeb_file on oeb01 = ogb31 and oeb03 = ogb32
where omaconf='Y' and omavoid = 'N' and oma00 = '12' 
 	and to_char(oma02, 'YYYY-MM-DD') between '2025-10-01' and '2025-10-31'
 	--and occ04 = 'T0005'
--group by oma03, occ02
;