package cn.zhouhaixian.bookingapi.dto;

import cn.zhouhaixian.bookingapi.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String name;

    private String password;

    private String phone;

    private User.Gender gender;

    private String subject;

    private User.Role role;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
