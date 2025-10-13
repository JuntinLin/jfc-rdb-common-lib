package com.jfc.rdb.common.dto;

import org.springframework.http.ResponseEntity;

public abstract class AbstractDTOController<D> {
	protected <R> ResponseEntity<ApiResponse<R>> success(R data) {
        return ResponseEntity.ok(new ApiResponse<>(true, data, "Success", "200"));
    }

    protected <R> ResponseEntity<ApiResponse<R>> error(String message) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, null, message, "400"));
    }
}
