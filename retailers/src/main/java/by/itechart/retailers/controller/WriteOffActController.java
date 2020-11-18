package by.itechart.retailers.controller;

import by.itechart.retailers.dto.WriteOffActDto;
import by.itechart.retailers.service.interfaces.WriteOffActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/writeOffActs")
public class WriteOffActController {

    private final WriteOffActService writeOffActService;

    @Autowired
    public WriteOffActController(WriteOffActService writeOffActService) {
        this.writeOffActService = writeOffActService;
    }

    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(writeOffActService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{writeOffActId}")
    public ResponseEntity findById(@PathVariable(name = "writeOffActId") Long writeOffActId) {
        return new ResponseEntity<>(writeOffActService.findById(writeOffActId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody WriteOffActDto writeOffActDto) {
        return new ResponseEntity<>(writeOffActService.create(writeOffActDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody WriteOffActDto writeOffActDto) {
        return new ResponseEntity<>(writeOffActService.update(writeOffActDto), HttpStatus.OK);
    }

}
