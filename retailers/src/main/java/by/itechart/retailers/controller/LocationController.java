package by.itechart.retailers.controller;

import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class LocationController {

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity findAllItems() {
        return new ResponseEntity<>(locationService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{locationId}")
    public ResponseEntity findById(@PathVariable(name = "locationId") Long locationId) {
        return new ResponseEntity<>(locationService.findById(locationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody LocationDto locationDto) {
        return new ResponseEntity<>(locationService.create(locationDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody LocationDto locationDto) {
        return new ResponseEntity<>(locationService.update(locationDto), HttpStatus.OK);
    }
}
