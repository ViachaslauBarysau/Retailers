package by.itechart.retailers.controller;

import by.itechart.retailers.dto.ApplicationRecordDto;
import by.itechart.retailers.service.interfaces.ApplicationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicationProductRecords")
public class ApplicationRecordController {

    private final ApplicationRecordService applicationRecordService;

    @Autowired
    public ApplicationRecordController(ApplicationRecordService applicationRecordService) {
        this.applicationRecordService = applicationRecordService;
    }

    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(applicationRecordService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{applicationProductRecordId}")
    public ResponseEntity findById(@PathVariable(name = "applicationProductRecordId") Long applicationProductRecordId) {
        return new ResponseEntity<>(applicationRecordService.findById(applicationProductRecordId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ApplicationRecordDto applicationRecordDto) {
        return new ResponseEntity<>(applicationRecordService.create(applicationRecordDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody ApplicationRecordDto applicationRecordDto) {
        return new ResponseEntity<>(applicationRecordService.update(applicationRecordDto), HttpStatus.OK);
    }

}
