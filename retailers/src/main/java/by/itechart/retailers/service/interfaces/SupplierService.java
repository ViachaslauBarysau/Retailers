package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.SupplierDto;

import java.util.List;

public interface SupplierService {
    SupplierDto findById(long supplierId);

    List<SupplierDto> findAll();

    SupplierDto create(SupplierDto supplierDto);

    SupplierDto update(SupplierDto supplierDto);
}
