package cn.zhouhaixian.bookingapi.exception;

public class ConfigurationNotFoundException extends RuntimeException {
    public ConfigurationNotFoundException() {
        super("找不到配置");
    }
}
