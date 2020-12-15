package by.itechart.retailers.controller;

import by.itechart.retailers.dto.WriteOffActDto;
import by.itechart.retailers.service.interfaces.WriteOffActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static by.itechart.retailers.constant.UrlConstants.*;

@RestController
@RequestMapping(URL_API + URL_WRITE_OFF_ACTS)
public class WriteOffActController {

    private final WriteOffActService writeOffActService;

    @Autowired
    public WriteOffActController(WriteOffActService writeOffActService) {
        this.writeOffActService = writeOffActService;
    }

    @GetMapping(value = "/by_customer")
    public ResponseEntity findAllByCustoemr(@PageableDefault(sort = "actDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(writeOffActService.findAllByCustomer(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/by_location")
    public ResponseEntity findAllByLocation(@PageableDefault(sort = "actDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(writeOffActService.findAllByLocation(pageable), HttpStatus.OK);
    }

    @GetMapping(value = WRITE_OFF_ACT_ID_VALUE)
    public ResponseEntity findById(@PathVariable(name = WRITE_OFF_ACT_ID) Long writeOffActId) {
        return new ResponseEntity<>(writeOffActService.findById(writeOffActId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody WriteOffActDto writeOffActDto) {
        return new ResponseEntity<>(writeOffActService.create(writeOffActDto), HttpStatus.CREATED);
    }
}
