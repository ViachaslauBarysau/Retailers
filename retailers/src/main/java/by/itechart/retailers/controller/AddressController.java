package by.itechart.retailers.controller;

import by.itechart.retailers.dto.AddressDto;
import by.itechart.retailers.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(addressService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{addressId}")
    public ResponseEntity findById(@PathVariable(name = "addressId") Long addressId) {
        return new ResponseEntity<>(addressService.findById(addressId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.create(addressDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.update(addressDto), HttpStatus.OK);
    }

}
