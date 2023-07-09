package cn.zhouhaixian.bookingapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Configuration {
    private String key;
    private String value;
}
