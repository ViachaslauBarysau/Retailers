package by.itechart.retailers.controller;

import by.itechart.retailers.dto.SupplierApplicationDto;
import by.itechart.retailers.service.SupplierApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplierApplications")
public class SupplierApplicationController {

    private SupplierApplicationService supplierApplicationService;

    @Autowired
    public SupplierApplicationController(SupplierApplicationService supplierApplicationService) {
        this.supplierApplicationService = supplierApplicationService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(supplierApplicationService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{supplierApplicationId}")
    public ResponseEntity findById(@PathVariable(name = "supplierApplicationId") Long supplierApplicationId) {
        return new ResponseEntity<>(supplierApplicationService.findById(supplierApplicationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody SupplierApplicationDto supplierApplicationDto) {
        return new ResponseEntity<>(supplierApplicationService.create(supplierApplicationDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody SupplierApplicationDto supplierApplicationDto) {
        return new ResponseEntity<>(supplierApplicationService.update(supplierApplicationDto), HttpStatus.OK);
    }
}
