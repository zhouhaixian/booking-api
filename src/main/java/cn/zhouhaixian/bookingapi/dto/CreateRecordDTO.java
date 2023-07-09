package cn.zhouhaixian.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class CreateRecordDTO {
    @NotBlank(message = "姓名不能为空")
    @Length(min = 2, max = 16, message = "姓名长度应在2-16个字符之间")
    public String name;

    @NotBlank(message = "年级不能为空")
    @Length(min = 2, max = 16, message = "年级长度应在2-16个字符之间")
    public String grade;

    @NotNull(message = "班级不能为空")
    @Min(value = 1, message = "班级必须为整数，且大于0")
    @JsonProperty("class")
    public int classNumber;

    @NotBlank(message = "科目不能为空")
    @NotBlank(message = "科目不能为空")
    @Length(min = 2, max = 16, message = "学科长度应在2-16个字符之间")
    public String subject;

    @NotNull(message = "索引不能为空")
    @Min(value = 0, message = "索引必须为整数，且大于等于0")
    @Max(value = 7, message = "索引必须为整数，且小于等于7")
    @JsonProperty("index")
    public int locator;

    @NotNull(message = "上课时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
    @Future(message = "上课时间必须为未来时间")
    @JsonProperty("start_time")
    public LocalDateTime startTime;

    @NotNull(message = "下课时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
    @Future(message = "下课时间必须为未来时间")
    @JsonProperty("end_time")
    public LocalDateTime endTime;
}
