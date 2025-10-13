package com.jfc.rdb.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.jfc.rdb.common.entity.AbstractEntity;

@NoRepositoryBean
public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {

// 自定義共用的查詢方法
	Optional<T> findByIdAndDeletedFalse(Long id);

	List<T> findAllByDeletedFalse();

// 其他共用的資料庫操作方法
}