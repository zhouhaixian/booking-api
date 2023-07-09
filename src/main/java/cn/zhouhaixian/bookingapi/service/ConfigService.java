package cn.zhouhaixian.bookingapi.service;

import cn.zhouhaixian.bookingapi.entity.Configuration;
import cn.zhouhaixian.bookingapi.entity.Configurations;
import cn.zhouhaixian.bookingapi.exception.ConfigurationNotFoundException;
import cn.zhouhaixian.bookingapi.exception.DuplicateInitializationException;
import cn.zhouhaixian.bookingapi.mapper.ConfigMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class ConfigService {
    private final ConfigMapper configMapper;

    public ConfigService(@Autowired ConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    public void initialize(@NotNull Configurations configurations) throws DuplicateInitializationException {
        Objects.requireNonNull(configurations);
        if (!hasConfiguration()) {
            configMapper.insertConfig(configurations.toList());
        } else {
            throw new DuplicateInitializationException();
        }
    }

    public Configurations getConfigurations() throws ConfigurationNotFoundException {
        List<Configuration> configurations = configMapper.findAll();
        if (CollectionUtils.isEmpty(configurations)) {
            throw new ConfigurationNotFoundException();
        } else {
            return Configurations.fromList(configurations);
        }
    }

    public void update(@NotNull Configurations configurations) throws ConfigurationNotFoundException {
        Objects.requireNonNull(configurations);
        if (!hasConfiguration()) throw new ConfigurationNotFoundException();
        configMapper.updateConfig(configurations.toList());
    }

    public boolean hasConfiguration() {
        return !CollectionUtils.isEmpty(configMapper.findAll());
    }
}
