package com.jfc.rdb.tiptop.model.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.jfc.rdb.tiptop.entity.OhcFile; 
import com.jfc.rdb.tiptop.model.dto.ComplaintDTO;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
	@Mapping(source = "ohc01", target = "complaintNo")
    @Mapping(source = "ohc02", target = "complaintDate")
	//@Mapping(source = "ohc02", target = "complaintDate", qualifiedByName = "dateToString") 
    @Mapping(source = "ohc03", target = "status") //ohc03	varchar2(1)	目前狀態	目前狀態'0':未處理'1':處理中'2':結案
    @Mapping(source = "ohc06", target = "customerCode")
    @Mapping(source = "ohc061", target = "customerName")
    @Mapping(source = "ohc08", target = "productCode")
    @Mapping(source = "ohc081", target = "productName")
    @Mapping(source = "ohc11", target = "handler")
    ComplaintDTO toDto(OhcFile ohcFile);
	@Named("dateToString")
    default String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof java.sql.Date) {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date(date.getTime()));
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
	
}
