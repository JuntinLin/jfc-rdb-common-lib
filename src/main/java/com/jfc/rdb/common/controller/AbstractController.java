package com.jfc.rdb.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jfc.rdb.common.dto.ApiResponse;

@RestController
//3. 修改 AbstractController，使用泛型 T 而不是限制 AbstractEntity
//public abstract class AbstractController<T extends AbstractEntity>
public abstract class AbstractController<T> {
    
    
    protected ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    protected ResponseEntity<ApiResponse<Void>> error(String message) {
        return ResponseEntity.badRequest()
            .body(ApiResponse.error(message, "400"));
    }

}
