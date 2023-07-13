package cn.zhouhaixian.bookingapi.service;

import cn.zhouhaixian.bookingapi.dto.InitializeConfigDTO;
import cn.zhouhaixian.bookingapi.dto.UpdateConfigDTO;
import cn.zhouhaixian.bookingapi.dto.mapper.ConfigurationsMapper;
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

    public void initialize(@NotNull InitializeConfigDTO initializeConfigDTO) throws DuplicateInitializationException {
        Objects.requireNonNull(initializeConfigDTO);
        if (!hasConfiguration()) {
            Configurations configurations = ConfigurationsMapper.INSTANCE.initializeConfigDTOToConfigurations(initializeConfigDTO);
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

    public void update(@NotNull UpdateConfigDTO updateConfigDTO) throws ConfigurationNotFoundException {
        Objects.requireNonNull(updateConfigDTO);
        if (!hasConfiguration()) throw new ConfigurationNotFoundException();
        Configurations configurations = ConfigurationsMapper.INSTANCE.updateConfigDTOToConfigurations(updateConfigDTO);
        configMapper.updateConfig(configurations.toList());
    }

    public boolean hasConfiguration() {
        return !CollectionUtils.isEmpty(configMapper.findAll());
    }
}
