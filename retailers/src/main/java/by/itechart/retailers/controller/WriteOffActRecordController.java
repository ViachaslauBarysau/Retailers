package by.itechart.retailers.controller;

import by.itechart.retailers.dto.WriteOffActRecordDto;
import by.itechart.retailers.service.interfaces.WriteOffActRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/writeOffActProductRecords")
public class WriteOffActRecordController {

    private WriteOffActRecordService writeOffActRecordService;

    @Autowired
    public WriteOffActRecordController(WriteOffActRecordService writeOffActRecordService) {
        this.writeOffActRecordService = writeOffActRecordService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(writeOffActRecordService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{writeOffActProductRecordId}")
    public ResponseEntity findById(@PathVariable(name = "writeOffActProductRecordId") Long writeOffActProductRecordId) {
        return new ResponseEntity<>(writeOffActRecordService.findById(writeOffActProductRecordId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody WriteOffActRecordDto writeOffActRecordDto) {
        return new ResponseEntity<>(writeOffActRecordService.create(writeOffActRecordDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody WriteOffActRecordDto writeOffActRecordDto) {
        return new ResponseEntity<>(writeOffActRecordService.update(writeOffActRecordDto), HttpStatus.OK);
    }
}
