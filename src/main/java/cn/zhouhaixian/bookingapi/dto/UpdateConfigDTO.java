package cn.zhouhaixian.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateConfigDTO {
    @Nullable
    @Length(max = 256, message = "预约成功提示信息长度不能超过256个字符")
    @JsonProperty("booking_success_message")
    private String bookingSuccessMessage;

    @Nullable
    @Length(max = 256, message = "忘记密码提示信息长度不能超过256个字符")
    @JsonProperty("forgot_password_message")
    private String forgotPasswordMessage;

    @Nullable
    @Length(max = 256, message = "预约频率过高提示信息长度不能超过256个字符")
    @JsonProperty("booking_limited_message")
    private String bookingLimitedMessage;
}
