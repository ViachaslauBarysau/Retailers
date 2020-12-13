package by.itechart.retailers.controller;

import by.itechart.retailers.service.interfaces.SupplierWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.itechart.retailers.constant.UrlConstants.URL_API;
import static by.itechart.retailers.constant.UrlConstants.URL_SUPPLIER_WAREHOUSES;

@RestController
@RequestMapping(URL_API + URL_SUPPLIER_WAREHOUSES)
public class SupplierWarehouseController {

    private final SupplierWarehouseService supplierWarehouseService;

    @Autowired
    public SupplierWarehouseController(SupplierWarehouseService supplierWarehouseService) {
        this.supplierWarehouseService = supplierWarehouseService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(supplierWarehouseService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{supplier_warehouse_id}")
    public ResponseEntity findById(@PathVariable(name = "supplier_warehouse_id") Long supplierWarehouseId) {
        return new ResponseEntity<>(supplierWarehouseService.findById(supplierWarehouseId), HttpStatus.OK);
    }
}
