package cn.zhouhaixian.bookingapi.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class Record {
    @Setter(AccessLevel.NONE)
    private long id;

    private String name;

    private String phone;

    private String grade;

    private int classNumber;

    private String subject;

    private int locator;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createdAt;
}
