package by.itechart.retailers.controller;

import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.service.interfaces.InnerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inner_applications")
public class InnerApplicationController {

    private final InnerApplicationService innerApplicationService;

    @Autowired
    public InnerApplicationController(InnerApplicationService innerApplicationService) {
        this.innerApplicationService = innerApplicationService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "registrationDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(innerApplicationService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{innerApplicationId}")
    public ResponseEntity findById(@PathVariable(name = "innerApplicationId") Long innerApplicationId) {
        return new ResponseEntity<>(innerApplicationService.findById(innerApplicationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody InnerApplicationDto innerApplicationDto) {
        return new ResponseEntity<>(innerApplicationService.create(innerApplicationDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody InnerApplicationDto innerApplicationDto) {
        return new ResponseEntity<>(innerApplicationService.update(innerApplicationDto), HttpStatus.OK);
    }

    @PutMapping(value = "/status")
    public ResponseEntity updateStatus(@RequestBody Long innerApplicationId) {
        return new ResponseEntity<>(innerApplicationService.updateStatus(innerApplicationId), HttpStatus.OK);
    }

}
