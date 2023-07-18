package cn.zhouhaixian.bookingapi.controller;

import cn.zhouhaixian.bookingapi.dto.CreateRecordDTO;
import cn.zhouhaixian.bookingapi.dto.DeleteRecordDTO;
import cn.zhouhaixian.bookingapi.dto.RecordDTO;
import cn.zhouhaixian.bookingapi.entity.Record;
import cn.zhouhaixian.bookingapi.service.RecordService;
import cn.zhouhaixian.bookingapi.utils.AdminOrOwnerAuthenticator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "RecordController", description = "预约记录")
@CrossOrigin("*")
@RestController
@RequestMapping("/record")
public class RecordController {
    private final RecordService recordService;

    public RecordController(@Autowired RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("")
    public CreateRecordDTO create(@RequestBody @Valid CreateRecordDTO createRecordDTO) {
        recordService.create(createRecordDTO);
        return createRecordDTO;
    }

    @GetMapping("schedule")
    public List<RecordDTO> findSevenDays() {
        return recordService.findSevenDays();
    }

    @SneakyThrows
    @GetMapping("{phone}")
    public List<RecordDTO> findByPhone(@PathVariable String phone) {
        AdminOrOwnerAuthenticator adminOrOwnerAuthenticator = new AdminOrOwnerAuthenticator();
        adminOrOwnerAuthenticator.authenticate(phone);
        return recordService.findByPhone(phone);
    }

    @GetMapping("all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RecordDTO> findAll(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "phone", required = false) String phone,
                                @RequestParam(value = "grade", required = false) String grade,
                                @RequestParam(value = "class", required = false) Integer classNumber,
                                @RequestParam(value = "subject", required = false) String subject,
                                @RequestParam(value = "index[]", required = false) Integer[] locators) {
        return recordService.findAll(name, phone, grade, classNumber, subject, locators);
    }

    @DeleteMapping("")
    public void delete(@RequestBody @Valid DeleteRecordDTO deleteRecordDTO) throws IllegalAccessException {
        recordService.delete(deleteRecordDTO);
    }
}
