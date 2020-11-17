package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CustomerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerDto findById(long customerId);

    List<CustomerDto> findAll(Pageable pageable);

    CustomerDto create(CustomerDto customerDto);

    CustomerDto update(CustomerDto customerDto);

    List<CustomerDto> updateStatus(List<Long> customerIds);
}
