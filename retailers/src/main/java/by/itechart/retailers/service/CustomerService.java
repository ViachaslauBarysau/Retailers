package by.itechart.retailers.service;

import by.itechart.retailers.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto findById(long customerId);

    List<CustomerDto> findAll();

    CustomerDto create(CustomerDto customerDto);

    CustomerDto update(CustomerDto customerDto);

    List<CustomerDto> updateStatus(List<CustomerDto> customerDtos);
}
