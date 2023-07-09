package cn.zhouhaixian.bookingapi.dto;

import cn.zhouhaixian.bookingapi.entity.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateUserDTO {
    @Nullable
    @Length(min = 2, max = 16, message = "姓名长度应在2-16个字符之间")
    public String name;

    @Nullable
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式错误")
    public String phone;

    @Nullable
    @Length(min = 8, max = 25, message = "密码长度应在8-25位之间")
    public String password;

    @Nullable
    public User.Gender gender;

    @Nullable
    @Length(min = 2, max = 32, message = "科目长度应在2-32个字符之间")
    public String subject;

    @Nullable
    User.Role role;
}
