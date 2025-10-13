package com.jfc.rdb.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private String code;

    // 成功響應的構造方法
    public ApiResponse(T data) {
        this.success = true;
        this.data = data;
        this.message = "Success";
        this.code = "200";
    }

    // 錯誤響應的構造方法
    public ApiResponse(String message, String code) {
        this.success = false;
        this.message = message;
        this.code = code;
    }

    // 靜態工廠方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> error(String message, String code) {
        return new ApiResponse<>(message, code);
    }
}
