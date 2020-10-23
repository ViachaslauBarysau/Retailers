package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CustomerConverter customerConverter;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerConverter customerConverter) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
    }


    @Override
    public CustomerDto findById(long customerId) {
        return null;
        //  return customerConverter.entityToDto(customerRepository.findById());
        //return null;
    }

    @Override
    public List<CustomerDto> findAll() {
        return null;
        // return customerConverter.entityToDto(customerRepository.findAll());


        //return null;
        /*return ((List<Customer>) customerRepository
                .findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());*/
    }

    @Override
    public CustomerDto create(CustomerDto customerDto) {
        return null;
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        return null;
    }

}
