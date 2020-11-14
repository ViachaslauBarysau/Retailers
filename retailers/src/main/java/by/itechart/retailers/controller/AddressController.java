package by.itechart.retailers.controller;

import by.itechart.retailers.dto.AddressDto;
import by.itechart.retailers.service.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(addressService.findAll(pageable), HttpStatus.OK);
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
