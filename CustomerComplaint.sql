select * 
from ohc_file
where ohc03 = '2'
	and ohc02 >= TO_DATE('2025/10/01', 'YYYY/MM/DD'); 

select ohd01, count(*) as ohd01_c
from ohd_file
group by ohd01
having count(*) > 5;

SELECT * 
from ohc_file
left outer join ohd_file on ohd01 = ohc01
where ohc01 = 'T391-23120002';
--客訴處理單單頭檔(ohc_file)&客訴單號單身檔(ohd_file)
SELECT ohc01, ohc02 , ohc03 --目前狀態'0':未處理'1':處理中'2':結案	
	, ohd.combined_ohd03_list
FROM ohc_file 
JOIN (select ohd01, LISTAGG(ohd03, ', ') WITHIN GROUP (ORDER BY ohd03) AS combined_ohd03_list 
	from ohd_file
	group by ohd01) ohd ON ohc01 = ohd.ohd01
where ohc01 = 'T391-23120002';

--客訴經手人員記錄檔(ohf_file)
select ohf01, ohf02 --類別0.客訴原因1.調查結果2.處理對策及改善對策3.審核4.核決5.結案註記
	, ohf03, gen02 --主辦人員
	, ohf04 -- 審核人員
	, ohf05 -- 責任單位
	, ohg.combined_ohg04_list
from ohf_file
join gen_file on gen01 = ohf03
join (select ohg01, ohg02, LISTAGG(ohg04, ', ') WITHIN GROUP (ORDER BY ohg01, ohg02) AS combined_ohg04_list 
	from ohg_file
	group by ohg01, ohg02) ohg ON ohf01 = ohg.ohg01 and ohf02 = ohg.ohg02
where ohf01 = 'T391-23120002' ;

select * from ock_file;