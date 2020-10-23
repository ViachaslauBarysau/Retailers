package by.itechart.retailers.controller;

import by.itechart.retailers.dto.ApplicationItemRecordDto;
import by.itechart.retailers.service.ApplicationItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicationItemRecords")
public class ApplicationItemRecordController {

    private ApplicationItemRecordService applicationItemRecordService;

    @Autowired
    public ApplicationItemRecordController(ApplicationItemRecordService applicationItemRecordService) {
        this.applicationItemRecordService = applicationItemRecordService;
    }

    @GetMapping
    public ResponseEntity findAllUsers() {
        return new ResponseEntity<>(applicationItemRecordService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{applicationItemRecordId}")
    public ResponseEntity findById(@PathVariable(name = "applicationItemRecordId") Long applicationItemRecordId) {
        return new ResponseEntity<>(applicationItemRecordService.findById(applicationItemRecordId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ApplicationItemRecordDto applicationItemRecordDto) {
        return new ResponseEntity<>(applicationItemRecordService.create(applicationItemRecordDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody ApplicationItemRecordDto applicationItemRecordDto) {
        return new ResponseEntity<>(applicationItemRecordService.update(applicationItemRecordDto), HttpStatus.OK);
    }

}
