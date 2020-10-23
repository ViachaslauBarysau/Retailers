package by.itechart.retailers.controller;

import by.itechart.retailers.dto.WriteOffActItemRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/writeOffActItemRecords")
public class WriteOffActItemRecordController {

    private WriteOffActItemRecordService writeOffActItemRecordService;

    @Autowired
    public WriteOffActItemRecordController(WriteOffActItemRecordService writeOffActItemRecordService) {
        this.writeOffActItemRecordService = writeOffActItemRecordService;
    }

    @GetMapping
    public ResponseEntity findAllUsers() {
        return new ResponseEntity<>(writeOffActItemRecordService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{writeOffActItemRecordId}")
    public ResponseEntity findById(@PathVariable(name = "writeOffActItemRecordId") Long writeOffActItemRecordId) {
        return new ResponseEntity<>(writeOffActItemRecordService.findById(writeOffActItemRecordId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody WriteOffActItemRecordDto writeOffActItemRecordDto) {
        return new ResponseEntity<>(writeOffActItemRecordService.create(writeOffActItemRecordDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody WriteOffActItemRecordDto writeOffActItemRecordDto) {
        return new ResponseEntity<>(writeOffActItemRecordService.update(writeOffActItemRecordDto), HttpStatus.OK);
    }
}
