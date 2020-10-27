package by.itechart.retailers.controller;

import by.itechart.retailers.dto.BillIRecordDto;
import by.itechart.retailers.service.BillRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billProductRecords")
public class BillRecordController {

    private BillRecordService billRecordService;

    @Autowired
    public BillRecordController(BillRecordService billRecordService) {
        this.billRecordService = billRecordService;
    }

    @GetMapping
    public ResponseEntity findAllUsers() {
        return new ResponseEntity<>(billRecordService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{billProductRecordId}")
    public ResponseEntity findById(@PathVariable(name = "billProductRecordId") Long billProductRecordId) {
        return new ResponseEntity<>(billRecordService.findById(billProductRecordId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody BillIRecordDto billIRecordDto) {
        return new ResponseEntity<>(billRecordService.create(billIRecordDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody BillIRecordDto billIRecordDto) {
        return new ResponseEntity<>(billRecordService.update(billIRecordDto), HttpStatus.OK);
    }
}
