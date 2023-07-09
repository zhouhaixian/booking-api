package cn.zhouhaixian.bookingapi.exception;

public class DuplicateInitializationException extends RuntimeException {
    public DuplicateInitializationException() {
        super("已被初始化");
    }
}
