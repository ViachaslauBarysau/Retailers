package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.SupplierDto;
import by.itechart.retailers.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private SupplierRepository supplierRepository;
    private CustomerConverter customerConverter;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, CustomerConverter customerConverter) {
        this.supplierRepository = supplierRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public SupplierDto findById(long supplierId) {
        return null;
    }

    @Override
    public List<SupplierDto> findAll() {
        return null;
    }

    @Override
    public SupplierDto create(SupplierDto supplierDto) {
        return null;
    }

    @Override
    public SupplierDto update(SupplierDto supplierDto) {
        return null;
    }
}
