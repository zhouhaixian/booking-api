package cn.zhouhaixian.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class DeleteRecordDTO {
    @NotNull(message = "索引不能为空")
    @Min(value = 0, message = "索引必须为整数，且大于等于0")
    @Max(value = 7, message = "索引必须为整数，且小于等于7")
    @JsonProperty("index")
    public int locator;

    @NotNull(message = "上课时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
    @JsonProperty("start_time")
    public LocalDateTime startTime;
}
