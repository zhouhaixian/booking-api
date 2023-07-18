package cn.zhouhaixian.bookingapi.mapper;

import cn.zhouhaixian.bookingapi.dto.DeleteRecordDTO;
import cn.zhouhaixian.bookingapi.entity.Record;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecordMapper {
    void insertRecord(Record record);

    List<Record> findRecordsWithTimeRangeAndPhone(LocalDateTime startTime, LocalDateTime endTime, String phone);

    List<Record> findRecordsBetweenTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    Record findRecordByLocatorAndStartTime(int locator, LocalDateTime startTime);

    List<Record> findRecordsByPhone(String phone);

    List<Record> findAll(String name, String phone, String grade, Integer classNumber, String subject, Integer[] locators);

    void deleteRecord(DeleteRecordDTO deleteRecordDTO);
}
