package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.SupplierConverter;
import by.itechart.retailers.dto.SupplierDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Status;
import by.itechart.retailers.entity.Supplier;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.SupplierRepository;
import by.itechart.retailers.service.interfaces.SupplierService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierConverter supplierConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierConverter supplierConverter, UserService userService) {
        this.supplierRepository = supplierRepository;
        this.supplierConverter = supplierConverter;
        this.userService = userService;
    }

    @Override
    public SupplierDto findById(long supplierId) {
        logger.info("Find by id {}",supplierId);
        Supplier supplier = supplierRepository.findById(supplierId)
                                              .orElse(new Supplier());

        return supplierConverter.entityToDto(supplier);
    }

    @Override
    public Page<SupplierDto> findAll(Pageable pageable) {
        logger.info("Find all");
        UserDto userDto = userService.getUser();
        Page<Supplier> supplierPage = supplierRepository.findAllByCustomer_Id(pageable, userDto.getCustomer()
                                                                                               .getId());
        List<SupplierDto> supplierDtos = supplierConverter.entityToDto(supplierPage.getContent());
        return new PageImpl<>(supplierDtos, pageable, supplierPage.getTotalElements());

    }

    @Override
    public SupplierDto create(SupplierDto supplierDto) throws BusinessException {
        logger.info("Create");
        Supplier supplier = supplierConverter.dtoToEntity(supplierDto);
        if (identifierExists(supplier.getIdentifier())) {
            logger.error("Not unique identifier {}", supplier.getIdentifier());
            throw new BusinessException("Identifier should be unique");
        }
        Supplier persistSupplier = supplierRepository.save(supplier);

        return supplierConverter.entityToDto(persistSupplier);
    }

    @Override
    public SupplierDto update(SupplierDto supplierDto) {
        logger.info("Update");
        Supplier supplier = supplierConverter.dtoToEntity(supplierDto);
        Supplier persistSupplier = supplierRepository.findById(supplier.getId())
                                                     .orElse(new Supplier());

        persistSupplier.setFullName(supplier.getFullName());
        persistSupplier.setWareHouseList(supplier.getWareHouseList());
        persistSupplier.setCustomer(supplier.getCustomer());
        persistSupplier.setSupplierStatus(supplier.getSupplierStatus());
        persistSupplier = supplierRepository.save(persistSupplier);

        return supplierConverter.entityToDto(persistSupplier);
    }

    @Override
    public List<SupplierDto> updateStatus(List<Long> supplierIdsList) {
        logger.info("Update status");
        List<Supplier> suppliers = supplierRepository.findAllById(supplierIdsList);
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierStatus()
                        .equals(Status.ACTIVE)) {
                supplier.setSupplierStatus(Status.DISABLED);
            } else {
                supplier.setSupplierStatus(Status.ACTIVE);
            }
            supplierRepository.save(supplier);
        }
        return supplierConverter.entityToDto(suppliers);
    }

    @Override
    public boolean identifierExists(String identifier) {
        logger.info("Check for exiting identifier {}", identifier);
        return supplierRepository.findByIdentifier(identifier)
                                 .isPresent();
    }
}
