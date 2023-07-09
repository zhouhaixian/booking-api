package cn.zhouhaixian.bookingapi.dto;

import cn.zhouhaixian.bookingapi.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateUserDTO {
    @NotBlank(message = "姓名不能为空")
    @Length(min = 2, max = 16, message = "姓名长度应在2-16个字符之间")
    public String name;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式错误")
    public String phone;

    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 25, message = "密码长度应在8-25位之间")
    public String password;

    @NotNull(message = "性别不能为空")
    public User.Gender gender;

    @NotBlank(message = "任教科目不能为空")
    @Length(min = 2, max = 32, message = "科目长度应在2-32个字符之间")
    public String subject;
}
