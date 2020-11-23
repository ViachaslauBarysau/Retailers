package by.itechart.retailers.controller;

import by.itechart.retailers.dto.LocationProductDto;
import by.itechart.retailers.service.interfaces.LocationProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location_products")
public class LocationProductController {

    private final LocationProductService locationProductService;

    @Autowired
    public LocationProductController(LocationProductService locationProductService) {
        this.locationProductService = locationProductService;
    }

    @GetMapping
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(locationProductService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{locationProductId}")
    public ResponseEntity findById(@PathVariable(name = "locationProductId") Long locationProductId) {
        return new ResponseEntity<>(locationProductService.findById(locationProductId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody LocationProductDto locationProductDto) {
        return new ResponseEntity<>(locationProductService.create(locationProductDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody LocationProductDto locationProductDto) {
        return new ResponseEntity<>(locationProductService.update(locationProductDto), HttpStatus.OK);
    }
}
