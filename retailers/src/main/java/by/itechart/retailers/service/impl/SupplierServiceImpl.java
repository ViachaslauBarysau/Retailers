package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.SupplierConverter;
import by.itechart.retailers.dto.SupplierDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Supplier;
import by.itechart.retailers.repository.SupplierRepository;
import by.itechart.retailers.service.interfaces.SupplierService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierConverter supplierConverter;
    private final UserService userService;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierConverter supplierConverter, UserService userService) {
        this.supplierRepository = supplierRepository;
        this.supplierConverter = supplierConverter;
        this.userService = userService;
    }

    @Override
    public SupplierDto findById(long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                                              .orElse(new Supplier());

        return supplierConverter.entityToDto(supplier);
    }

    @Override
    public List<SupplierDto> findAll(Pageable pageable) {
        UserDto userDto = userService.getUser();
        Page<Supplier> supplierPage = supplierRepository.findAllByCustomer_Id(pageable, userDto.getCustomer()
                                                                                               .getId());
        return supplierConverter.entityToDto(supplierPage.toList());
    }

    @Override
    public SupplierDto create(SupplierDto supplierDto) {
        Supplier supplier = supplierConverter.dtoToEntity(supplierDto);
        Supplier persistSupplier = supplierRepository.save(supplier);

        return supplierConverter.entityToDto(persistSupplier);
    }

    @Override
    public SupplierDto update(SupplierDto supplierDto) {
        Supplier supplier = supplierConverter.dtoToEntity(supplierDto);
        Supplier persistSupplier = supplierRepository.findById(supplier.getId())
                                                     .orElse(new Supplier());

        persistSupplier.setFullName(supplier.getFullName());
        persistSupplier.setIdentifier(supplier.getIdentifier());
        persistSupplier.setWareHouseList(supplier.getWareHouseList());
        persistSupplier.setCustomer(supplier.getCustomer());
        persistSupplier=supplierRepository.save(persistSupplier);

        return supplierConverter.entityToDto(persistSupplier);
    }
}
