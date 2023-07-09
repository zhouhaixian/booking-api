package cn.zhouhaixian.bookingapi.exception;

public class DuplicateBookingException extends RuntimeException {
    public DuplicateBookingException() {
        super("重复预约");
    }
}
