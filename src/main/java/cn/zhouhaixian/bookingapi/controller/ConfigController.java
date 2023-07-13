package cn.zhouhaixian.bookingapi.controller;

import cn.zhouhaixian.bookingapi.dto.InitializeConfigDTO;
import cn.zhouhaixian.bookingapi.dto.UpdateConfigDTO;
import cn.zhouhaixian.bookingapi.dto.mapper.ConfigurationsMapper;
import cn.zhouhaixian.bookingapi.service.ConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ConfigController", description = "配置")
@RestController
@RequestMapping("/config")
public class ConfigController {
    private ConfigService configService;

    public ConfigController(@Autowired ConfigService configService) {
        this.configService = configService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public InitializeConfigDTO initialize(@Valid @RequestBody InitializeConfigDTO initializeConfigDTO) {
        configService.initialize(initializeConfigDTO);
        return initializeConfigDTO;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("")
    public void update(@Valid @RequestBody UpdateConfigDTO updateConfigDTO) {
        configService.update(updateConfigDTO);
    }

    @GetMapping("")
    public InitializeConfigDTO getConfigurations() {
        return ConfigurationsMapper.INSTANCE.configurationsToInitializeConfigDTO(configService.getConfigurations());
    }
}
