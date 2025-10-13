package com.jfc.rdb.tiptop.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.jfc.rdb.tiptop.entity.EcaFile;
import com.jfc.rdb.tiptop.model.dto.EcaDTO;

@Mapper(componentModel = "spring")
public interface EcaMapper {
	// 獲取實例（當不使用依賴注入時）
    EcaMapper INSTANCE = Mappers.getMapper(EcaMapper.class);
    
    // Entity 轉 DTO
    @Mapping(target = "eca01", source="eca01")  
    @Mapping(target = "eca02", source = "eca02")
    @Mapping(target = "eca03", source = "eca03")
    @Mapping(target = "departmentName", source = "department.gem02")
    @Mapping(target = "departmentFullName", source = "department.gem03")
    EcaDTO toDTO(EcaFile entity);
    
    // DTO 轉 Entity
    @Mapping(target = "eca01", ignore = true)  // 忽略料號設置
    @Mapping(target = "department", ignore = true)
    EcaFile toEntity(EcaDTO dto);
    
    // 更新現有實體
    @Mapping(target = "eca01", ignore = true)
    @Mapping(target = "department", ignore = true)
    void updateEntityFromDto(EcaDTO dto, @MappingTarget EcaFile entity);
}
