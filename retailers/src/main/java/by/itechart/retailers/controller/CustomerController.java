package by.itechart.retailers.controller;

import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.service.interfaces.CustomerService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;

    @Autowired
    public CustomerController(CustomerService customerService, UserService userService) {
        this.customerService = customerService;
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(customerService.findAll(pageable), HttpStatus.OK);
    }
    @GetMapping(value = "/customer_employees")
    public ResponseEntity findAllCustomerEmployees(Pageable pageable) {
        return new ResponseEntity<>(userService.findAllByCustomerId(pageable), HttpStatus.OK);
    }
    @GetMapping(value = "/{customerId}")
    public ResponseEntity findById(@PathVariable(name = "customerId") Long customerId) {
        return new ResponseEntity<>(customerService.findById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.create(customerDto), HttpStatus.CREATED);
    }

  /*   @PutMapping
    public ResponseEntity update(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.update(customerDto), HttpStatus.OK);
    }*/

    @PutMapping
    public ResponseEntity updateStatus(@RequestBody List<CustomerDto> customerDtos) {
        return new ResponseEntity<>(customerService.updateStatus(customerDtos), HttpStatus.OK);
    }
}
