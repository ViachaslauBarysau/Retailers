package by.itechart.retailers.controller;

import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.service.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static by.itechart.retailers.constant.UrlConstants.URL_API;
import static by.itechart.retailers.constant.UrlConstants.URL_LOCATIONS;

@RestController
@RequestMapping(URL_API + URL_LOCATIONS)
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "identifier", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(locationService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/warehouses")
    public ResponseEntity findAllWarehouses(@PageableDefault(sort = "identifier", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(locationService.findAllWarehouses(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/shops")
    public ResponseEntity findAllShops(@PageableDefault(sort = "identifier", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(locationService.findAllShops(pageable), HttpStatus.OK);
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

    @DeleteMapping
    public ResponseEntity delete(@RequestBody List<Long> locationIds) {
        return new ResponseEntity<>(locationService.delete(locationIds), HttpStatus.OK);
    }
}
