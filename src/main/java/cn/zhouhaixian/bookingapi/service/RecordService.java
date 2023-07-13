package cn.zhouhaixian.bookingapi.service;

import cn.zhouhaixian.bookingapi.dto.CreateRecordDTO;
import cn.zhouhaixian.bookingapi.dto.DeleteRecordDTO;
import cn.zhouhaixian.bookingapi.entity.Record;
import cn.zhouhaixian.bookingapi.exception.DuplicateBookingException;
import cn.zhouhaixian.bookingapi.exception.FrequentBookingException;
import cn.zhouhaixian.bookingapi.mapper.RecordMapper;
import cn.zhouhaixian.bookingapi.utils.AdminOrOwnerAuthenticator;
import cn.zhouhaixian.bookingapi.utils.TimeUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class RecordService {
    private final RecordMapper recordMapper;

    public RecordService(@Autowired RecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }

    public void create(@NotNull CreateRecordDTO createRecordDTO) {
        Objects.requireNonNull(createRecordDTO);
        LocalDateTime startTime = createRecordDTO.getStartTime();
        LocalDateTime endTime = createRecordDTO.getEndTime();
        if (TimeUtils.isBetweenNextSevenDays(startTime) && TimeUtils.isBetweenNextSevenDays(endTime) && startTime.isBefore(endTime)) {
            if (!isTimeBooked(createRecordDTO)) {
                AdminOrOwnerAuthenticator adminOrOwnerAuthenticator = new AdminOrOwnerAuthenticator();
                String phone = adminOrOwnerAuthenticator.getPhone();
                if (!adminOrOwnerAuthenticator.isAdmin()) {
                    List<Record> recordsBetweenNextSevenDays = findSevenDays(phone);
                    if (recordsBetweenNextSevenDays.size() > 1) {
                        throw new FrequentBookingException();
                    }
                }
                Record record = cn.zhouhaixian.bookingapi.dto.mapper.RecordMapper
                        .INSTANCE.createRecordDTOToRecord(createRecordDTO);
                record.setPhone(phone);
                recordMapper.insertRecord(record);
            } else {
                throw new DuplicateBookingException();
            }
        } else {
            throw new IllegalArgumentException("预约时间不在未来七天内或者下课时间比上课时间早");
        }
    }

    public List<Record> findByPhone(@NotNull String phone) {
        Objects.requireNonNull(phone);
        return recordMapper.findRecordsByPhone(phone);
    }


    public List<Record> findAll() {
        return recordMapper.findAll();
    }

    public void delete(@NotNull DeleteRecordDTO deleteRecordDTO) throws IllegalAccessException {
        Objects.requireNonNull(deleteRecordDTO);
        Record record = recordMapper.findRecordByLocatorAndStartTime(deleteRecordDTO.getLocator(), deleteRecordDTO.getStartTime());
        if (Objects.isNull(record)) {
            throw new IllegalArgumentException("预约记录不存在");
        }
        AdminOrOwnerAuthenticator adminOrOwnerAuthenticator = new AdminOrOwnerAuthenticator();
        if (!adminOrOwnerAuthenticator.isAdmin() && !adminOrOwnerAuthenticator.isOwner(record.getPhone())) {
            throw new IllegalAccessException("只有管理员或者预约者才能删除预约记录");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        if (!adminOrOwnerAuthenticator.isAdmin() && currentTime.isAfter(record.getStartTime())) {
            throw new IllegalAccessException("只有管理员才能删除过去的预约记录");
        }
        recordMapper.deleteRecord(deleteRecordDTO);
    }

    public boolean isTimeBooked(@NotNull CreateRecordDTO createRecordDTO) {
        Objects.requireNonNull(createRecordDTO);
        LocalDateTime dayStart = LocalDateTime.of(createRecordDTO.getStartTime().toLocalDate(), LocalTime.MIN);
        LocalDateTime dayEnd = LocalDateTime.of(createRecordDTO.getStartTime().toLocalDate(), LocalTime.MAX);
        List<Record> records = recordMapper.findRecordsBetweenTimeRange(dayStart, dayEnd);
        return records.stream().anyMatch(r -> r.getLocator() == createRecordDTO.getLocator());
    }

    public List<Record> findSevenDays(@NotNull String phone) {
        Objects.requireNonNull(phone);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime sevenDaysLater = currentTime.plusDays(7);
        return recordMapper.findRecordsWithTimeRangeAndPhone(currentTime, sevenDaysLater, phone);
    }

    public List<Record> findSevenDays() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime sevenDaysLater = currentTime.plusDays(7);
        return recordMapper.findRecordsBetweenTimeRange(currentTime, sevenDaysLater);
    }
}
