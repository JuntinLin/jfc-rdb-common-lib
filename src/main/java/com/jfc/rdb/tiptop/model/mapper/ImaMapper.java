package com.jfc.rdb.tiptop.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.jfc.rdb.tiptop.entity.ImaFile;
import com.jfc.rdb.tiptop.model.dto.ImaDTO;

@Mapper(componentModel = "spring")
public interface  ImaMapper {
	// 獲取實例（當不使用依賴注入時）
    ImaMapper INSTANCE = Mappers.getMapper(ImaMapper.class);
    
    // Entity 轉 DTO
    @Mapping(target = "ima01", source="ima01")  
    @Mapping(target = "ima02", source = "ima02")
    @Mapping(target = "ima021", source = "ima021")
    @Mapping(target = "ima27", source = "ima27")
    ImaDTO toDTO(ImaFile entity);
    
    // DTO 轉 Entity
    @Mapping(target = "ima01", ignore = true)  // 忽略料號設置
    ImaFile toEntity(ImaDTO dto);
    
    // 更新現有實體
    @Mapping(target = "ima01", ignore = true)
    void updateEntityFromDto(ImaDTO dto, @MappingTarget ImaFile entity);
}
