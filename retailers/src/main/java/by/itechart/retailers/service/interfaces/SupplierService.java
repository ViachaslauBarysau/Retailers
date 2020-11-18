package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.SupplierDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {
    SupplierDto findById(long supplierId);

    List<SupplierDto> findAll(Pageable pageable);

    SupplierDto create(SupplierDto supplierDto);

    SupplierDto update(SupplierDto supplierDto);

    List<SupplierDto> updateStatus(List<Long> supplierIdsList);
}
