package cn.zhouhaixian.bookingapi.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class User {
    @Setter(AccessLevel.NONE)
    private long id;

    private String name;

    private String password;

    private String phone;

    private Gender gender;

    private String subject;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public enum Gender {
        FEMALE, MALE
    }

    public enum Role {
        ADMIN, USER
    }
}