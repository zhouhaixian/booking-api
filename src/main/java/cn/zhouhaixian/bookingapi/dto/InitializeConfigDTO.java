package cn.zhouhaixian.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class InitializeConfigDTO {
    @NotBlank(message = "预约成功提示信息不能为空")
    @Length(max = 256, message = "预约成功提示信息长度不能超过256个字符")
    @JsonProperty("booking_success_message")
    private String bookingSuccessMessage;

    @NotBlank(message = "忘记密码提示信息不能为空")
    @Length(max = 256, message = "忘记密码提示信息长度不能超过256个字符")
    @JsonProperty("forgot_password_message")
    private String forgotPasswordMessage;

    @NotBlank(message = "预约频率过高提示信息不能为空")
    @Length(max = 256, message = "预约频率过高提示信息长度不能超过256个字符")
    @JsonProperty("booking_limited_message")
    private String bookingLimitedMessage;
}
