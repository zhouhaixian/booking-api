package cn.zhouhaixian.bookingapi.controller;

import cn.zhouhaixian.bookingapi.dto.CreateRecordDTO;
import cn.zhouhaixian.bookingapi.dto.DeleteRecordDTO;
import cn.zhouhaixian.bookingapi.dto.mapper.RecordMapper;
import cn.zhouhaixian.bookingapi.entity.Record;
import cn.zhouhaixian.bookingapi.service.RecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "RecordController", description = "预约记录")
@RestController
@RequestMapping("/record")
public class RecordController {
    private final RecordService recordService;

    public RecordController(@Autowired RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("")
    public CreateRecordDTO create(@RequestBody @Valid CreateRecordDTO createRecordDTO) {
        Record record = RecordMapper.INSTANCE.createRecordDTOToRecord(createRecordDTO);
        recordService.create(record);
        return createRecordDTO;
    }

    @DeleteMapping("")
    public void delete(@RequestBody @Valid DeleteRecordDTO deleteRecordDTO) throws IllegalAccessException {
        recordService.delete(deleteRecordDTO);
    }
}
