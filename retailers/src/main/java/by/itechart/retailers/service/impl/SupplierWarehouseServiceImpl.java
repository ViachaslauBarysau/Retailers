package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.SupplierWarehouseConverter;
import by.itechart.retailers.dto.SupplierWarehouseDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.entity.SupplierWarehouse;
import by.itechart.retailers.repository.SupplierWarehouseRepository;
import by.itechart.retailers.service.interfaces.SupplierWarehouseService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierWarehouseServiceImpl implements SupplierWarehouseService {
    private final SupplierWarehouseRepository supplierWarehouseRepository;
    private final SupplierWarehouseConverter supplierWarehouseConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(SupplierWarehouseServiceImpl.class);

    @Autowired
    public SupplierWarehouseServiceImpl(SupplierWarehouseRepository supplierWarehouseRepository, SupplierWarehouseConverter supplierWarehouseConverter, UserService userService) {
        this.supplierWarehouseRepository = supplierWarehouseRepository;
        this.supplierWarehouseConverter = supplierWarehouseConverter;
        this.userService = userService;
    }

    @Override
    public List<SupplierWarehouseDto> findAll() {
        logger.info("Find all");
        UserDto userDto = userService.getUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        List<SupplierWarehouse> supplierWarehouses = supplierWarehouseRepository.findAllByCustomer_IdAndStatus(customerId, DeletedStatus.ACTIVE);
        return supplierWarehouseConverter.entityToDto(supplierWarehouses);
    }

    @Override
    public SupplierWarehouseDto findById(Long supplierWarehouseId) {
        logger.info("Find by id {}", supplierWarehouseId);
        SupplierWarehouse supplierWarehouse = supplierWarehouseRepository.findById(supplierWarehouseId)
                                                                         .orElse(new SupplierWarehouse());

        return supplierWarehouseConverter.entityToDto(supplierWarehouse);
    }

    @Override
    public boolean identifierExists(String identifier) {
        return false;
    }
}
