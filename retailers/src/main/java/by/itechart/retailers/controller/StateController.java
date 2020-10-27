package by.itechart.retailers.controller;

import by.itechart.retailers.dto.StateDto;
import by.itechart.retailers.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/states")
public class StateController {

    private StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity findAllProducts() {
        return new ResponseEntity<>(stateService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{stateId}")
    public ResponseEntity findById(@PathVariable(name = "stateId") Long stateId) {
        return new ResponseEntity<>(stateService.findById(stateId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody StateDto stateDto) {
        return new ResponseEntity<>(stateService.create(stateDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody StateDto stateDto) {
        return new ResponseEntity<>(stateService.update(stateDto), HttpStatus.OK);
    }
}