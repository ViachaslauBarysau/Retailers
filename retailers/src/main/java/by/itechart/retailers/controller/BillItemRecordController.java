package by.itechart.retailers.controller;

import by.itechart.retailers.dto.BillItemRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biilItemRecords")
public class BillItemRecordController {

    private BillItemRecordService billItemRecordService;

    @Autowired
    public BillItemRecordController(BillItemRecordService billItemRecordService) {
        this.billItemRecordService = billItemRecordService;
    }

    @GetMapping
    public ResponseEntity findAllUsers() {
        return new ResponseEntity<>(billItemRecordService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{billItemRecordId}")
    public ResponseEntity findById(@PathVariable(name = "billItemRecordId") Long billItemRecordId) {
        return new ResponseEntity<>(billItemRecordService.findById(billItemRecordId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody BillItemRecordDto billItemRecordDto) {
        return new ResponseEntity<>(billItemRecordService.create(billItemRecordDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody BillItemRecordDto billItemRecordDto) {
        return new ResponseEntity<>(billItemRecordService.update(billItemRecordDto), HttpStatus.OK);
    }
}
