package cn.zhouhaixian.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordDTO {

    private String name;

    private String phone;

    private String grade;

    @JsonProperty("class")
    private int classNumber;

    private String subject;

    @JsonProperty("index")
    private int locator;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
