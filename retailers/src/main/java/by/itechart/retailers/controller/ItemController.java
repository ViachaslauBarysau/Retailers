package by.itechart.retailers.controller;

import by.itechart.retailers.dto.ItemDto;
import by.itechart.retailers.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity findAllItems() {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{itemId}")
    public ResponseEntity findById(@PathVariable(name = "itemId") Long itemId) {
        return new ResponseEntity<>(itemService.findById(itemId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ItemDto itemDto) {
        return new ResponseEntity<>(itemService.create(itemDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody ItemDto itemDto) {
        return new ResponseEntity<>(itemService.update(itemDto), HttpStatus.OK);
    }
}
