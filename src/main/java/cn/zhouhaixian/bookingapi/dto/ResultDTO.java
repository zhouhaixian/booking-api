package cn.zhouhaixian.bookingapi.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResultDTO<T> {
    private int code;
    private String message;
    private T data;
    private long timestamp;

    public enum Status {
        SUCCESS(HttpStatus.OK.value(), "请求成功"),
        BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "请求错误"),
        UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "认证失败"),
        FORBIDDEN(HttpStatus.FORBIDDEN.value(), "权限不足"),
        USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "用户不存在"),
        CONFIGURATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "找不到配置"),
        DUPLICATE_INITIALIZATION(HttpStatus.CONFLICT.value(), "重复初始化"),
        FREQUENT_BOOKING(HttpStatus.TOO_MANY_REQUESTS.value(), "预约过于频繁"),
        DUPLICATE_BOOKING(HttpStatus.CONFLICT.value(), "重复预约"),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误");

        private final int code;
        private final String message;

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public ResultDTO(Status status, T data) {
        setCode(status.getCode());
        setMessage(status.getMessage());
        setData(data);
        setTimestamp(System.currentTimeMillis());
    }

    public ResultDTO(int code, String message) {
        setCode(code);
        setMessage(message);
        setData(null);
        setTimestamp(System.currentTimeMillis());
    }

    public ResultDTO(Status status) {
        setCode(status.getCode());
        setMessage(status.getMessage());
        setData(null);
        setTimestamp(System.currentTimeMillis());
    }

    public static <T> ResultDTO<T> success(T data) {
        return new ResultDTO<>(Status.SUCCESS, data);
    }
}
