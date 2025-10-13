package com.jfc.rdb.hrm.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jfc.rdb.hrm.model.dto.AttendanceStatisticsDTO;

@Component
public class AttendanceStatisticsMapper {
	/**
     * Convert Object[] result from native query to AttendanceStatisticsDTO
     */
    public AttendanceStatisticsDTO mapToDTO(Object[] result) {
        AttendanceStatisticsDTO dto = new AttendanceStatisticsDTO();
        
        dto.setCol1(getString(result[0]));
        dto.setDepartmentCode(getString(result[1]));
        dto.setEmployeeCode(getString(result[2]));
        dto.setDepartmentName(getString(result[3]));
        dto.setEmployeeName(getString(result[4]));
        dto.setItemName(getString(result[5]));
        dto.setValue(getBigDecimal(result[6]));
        dto.setCorporationName(getString(result[7]));
        dto.setPeriod(getString(result[8]));
        dto.setDate(getDate(result[9]));
        dto.setShift(getString(result[10]));
        
        return dto;
    }
    
    /**
     * Convert list of Object[] results to list of DTOs
     */
    public List<AttendanceStatisticsDTO> mapToDTOList(List<Object[]> results) {
        return results.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    private String getString(Object obj) {
        return obj != null ? obj.toString() : "";
    }
    
    private BigDecimal getBigDecimal(Object obj) {
        if (obj == null) return BigDecimal.ZERO;
        if (obj instanceof BigDecimal) return (BigDecimal) obj;
        if (obj instanceof Number) return new BigDecimal(obj.toString());
        return BigDecimal.ZERO;
    }
    
    private Date getDate(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Date) return (Date) obj;
        if (obj instanceof java.sql.Date) return new Date(((java.sql.Date) obj).getTime());
        if (obj instanceof java.sql.Timestamp) return new Date(((java.sql.Timestamp) obj).getTime());
        return null;
    }
}
