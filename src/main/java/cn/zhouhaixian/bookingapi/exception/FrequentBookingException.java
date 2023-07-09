package cn.zhouhaixian.bookingapi.exception;

public class FrequentBookingException extends RuntimeException {
    public FrequentBookingException() {
        super("普通用户一周内最多预约两次");
    }
}
