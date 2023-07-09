package cn.zhouhaixian.bookingapi.dto.mapper;

import cn.zhouhaixian.bookingapi.dto.CreateRecordDTO;
import cn.zhouhaixian.bookingapi.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecordMapper {
    RecordMapper INSTANCE = Mappers.getMapper(RecordMapper.class);

    Record createRecordDTOToRecord(CreateRecordDTO createRecordDTO);
}
