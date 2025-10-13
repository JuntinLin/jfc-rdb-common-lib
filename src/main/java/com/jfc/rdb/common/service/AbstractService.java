package com.jfc.rdb.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfc.rdb.common.entity.AbstractEntity;
import com.jfc.rdb.common.repository.AbstractRepository;

import jakarta.persistence.EntityNotFoundException;

public abstract class AbstractService<T extends AbstractEntity> {
	@Autowired
    protected AbstractRepository<T> repository;
    
    // 共用的業務邏輯方法
    public T findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException());
    }
    
    public List<T> findAll() {
        return repository.findAll();
    }
    
    public T save(T entity) {
        // 通用的保存邏輯，如驗證、日誌等
        return repository.save(entity);
    }
    
    // 其他共用的服務方法
}
