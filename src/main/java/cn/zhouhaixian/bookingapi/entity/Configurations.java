package cn.zhouhaixian.bookingapi.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class Configurations {
    private String bookingSuccessMessage;

    private String forgotPasswordMessage;

    private String bookingLimitedMessage;

    private static final String bookingSuccessMessageKey = "booking_success_message";

    private static final String forgotPasswordMessageKey = "forgot_password_message";

    private static final String bookingLimitedMessageKey = "booking_limited_message";

    private Configuration getBookingSuccessMessageConfiguration() {
        return new Configuration(bookingSuccessMessageKey, getBookingSuccessMessage());
    }

    private Configuration getForgotPasswordMessageConfiguration() {
        return new Configuration(forgotPasswordMessageKey, getForgotPasswordMessage());
    }

    private Configuration getBookingLimitedMessageConfiguration() {
        return new Configuration(bookingLimitedMessageKey, getBookingLimitedMessage());
    }

    public List<Configuration> toList() {
        return List.of(getBookingSuccessMessageConfiguration(), getForgotPasswordMessageConfiguration(), getBookingLimitedMessageConfiguration());
    }

    public static Configurations fromList(List<Configuration> configurations) {
        Configurations configs = new Configurations();
        for (Configuration config : configurations) {
            switch (config.getKey()) {
                case bookingSuccessMessageKey -> configs.setBookingSuccessMessage(config.getValue());
                case forgotPasswordMessageKey -> configs.setForgotPasswordMessage(config.getValue());
                case bookingLimitedMessageKey -> configs.setBookingLimitedMessage(config.getValue());
            }
        }
        return configs;
    }
}
