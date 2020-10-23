package by.itechart.retailers.controller;

import by.itechart.retailers.dto.LocationItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locationItems")
public class LocationItemController {

    private LocationItemService locationItemService;

    @Autowired
    public LocationItemController(LocationItemService locationItemService) {
        this.locationItemService = locationItemService;
    }

    @GetMapping
    public ResponseEntity findAllItems() {
        return new ResponseEntity<>(locationItemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{locationItemId}")
    public ResponseEntity findById(@PathVariable(name = "locationItemId") Long locationItemId) {
        return new ResponseEntity<>(locationItemService.findById(locationItemId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody LocationItemDto locationItemDto) {
        return new ResponseEntity<>(locationItemService.create(locationItemDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody LocationItemDto locationItemDto) {
        return new ResponseEntity<>(locationItemService.update(locationItemDto), HttpStatus.OK);
    }
}
