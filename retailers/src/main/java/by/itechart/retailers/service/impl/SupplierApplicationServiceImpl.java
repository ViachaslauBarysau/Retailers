package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.SupplierApplicationDto;
import by.itechart.retailers.service.SupplierApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierApplicationServiceImpl implements SupplierApplicationService {

    private SupplierApplicationRepository supplierApplicationRepository;
    private CustomerConverter customerConverter;

    @Autowired
    public SupplierApplicationServiceImpl(SupplierApplicationRepository supplierApplicationRepository, CustomerConverter customerConverter) {
        this.supplierApplicationRepository = supplierApplicationRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public SupplierApplicationDto findById(long supplierApplicationId) {
        return null;
    }

    @Override
    public List<SupplierApplicationDto> findAll() {
        return null;
    }

    @Override
    public SupplierApplicationDto create(SupplierApplicationDto supplierApplicationDto) {
        return null;
    }

    @Override
    public SupplierApplicationDto update(SupplierApplicationDto supplierApplicationDto) {
        return null;
    }
}
