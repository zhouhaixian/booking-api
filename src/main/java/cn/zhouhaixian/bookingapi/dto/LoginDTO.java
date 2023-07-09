package cn.zhouhaixian.bookingapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginDTO {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式错误")
    public String phone;

    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 25, message = "密码长度应在8-25位之间")
    public String password;
}
