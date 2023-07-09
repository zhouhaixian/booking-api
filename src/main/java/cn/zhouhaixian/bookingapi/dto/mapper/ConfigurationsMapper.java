package cn.zhouhaixian.bookingapi.dto.mapper;

import cn.zhouhaixian.bookingapi.dto.InitializeConfigDTO;
import cn.zhouhaixian.bookingapi.dto.UpdateConfigDTO;
import cn.zhouhaixian.bookingapi.entity.Configurations;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConfigurationsMapper {
    ConfigurationsMapper INSTANCE = Mappers.getMapper(ConfigurationsMapper.class);

    Configurations initializeConfigDTOToConfigurations(InitializeConfigDTO initializeConfigDTO);

    Configurations updateConfigDTOToConfigurations(UpdateConfigDTO updateConfigDTO);

    InitializeConfigDTO configurationsToInitializeConfigDTO(Configurations configurations);
}
