package by.itechart.retailers.controller;

import by.itechart.retailers.dto.SupplierDto;
import by.itechart.retailers.service.interfaces.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(supplierService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{supplierId}")
    public ResponseEntity findById(@PathVariable(name = "supplierId") Long supplierId) {
        return new ResponseEntity<>(supplierService.findById(supplierId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody SupplierDto supplierDto) {
        return new ResponseEntity<>(supplierService.create(supplierDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody SupplierDto supplierDto) {
        return new ResponseEntity<>(supplierService.update(supplierDto), HttpStatus.OK);
    }
}
