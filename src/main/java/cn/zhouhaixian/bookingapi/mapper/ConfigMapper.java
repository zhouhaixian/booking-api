package cn.zhouhaixian.bookingapi.mapper;

import cn.zhouhaixian.bookingapi.entity.Configuration;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConfigMapper {
    void insertConfig(@NotNull List<Configuration> configurations);

    void updateConfig(@NotNull List<Configuration> configurations);

    List<Configuration> findAll();
}
