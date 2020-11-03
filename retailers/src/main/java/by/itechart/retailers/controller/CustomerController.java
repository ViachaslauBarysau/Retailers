package by.itechart.retailers.controller;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(customerService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{customerId}")
    public ResponseEntity findById(@PathVariable(name = "customerId") Long customerId) {
        return new ResponseEntity<>(customerService.findById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.create(customerDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.update(customerDto), HttpStatus.OK);
    }
}
