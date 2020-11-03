package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.entity.Customer;
import by.itechart.retailers.entity.Status;
import by.itechart.retailers.repository.CustomerRepository;
import by.itechart.retailers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Customer customer = customerRepository.findById(customerId)
                                              .orElse(new Customer());
        return customerConverter.entityToDto(customer);
    }

    @Override
    public List<CustomerDto> findAll(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return customerConverter.entityToDto(customerPage.toList());
    }


    @Override
    public CustomerDto create(CustomerDto customerDto) {
        Customer customer = customerConverter.dtoToEntity(customerDto);
        Customer persistsCustomer = customerRepository.save(customer);
        return customerConverter.entityToDto(persistsCustomer);
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Customer customer = customerConverter.dtoToEntity(customerDto);
        Customer persistCustomer = customerRepository.findById(customer.getId())
                                                     .orElse(new Customer());

        persistCustomer.setAdmin(customer.getAdmin());
        persistCustomer.setCategoryList(customer.getCategoryList());
        persistCustomer.setDirector(customer.getDirector());
        persistCustomer.setLocationList(customer.getLocationList());
        persistCustomer.setCustomerStatus(customer.getCustomerStatus());
        persistCustomer.setProductList(customer.getProductList());

        return customerConverter.entityToDto(persistCustomer);
    }

    @Override
    public List<CustomerDto> updateStatus(List<CustomerDto> customerDtos) {
        List<Customer> customers = customerConverter.dtoToEntity(customerDtos);
        for (Customer customer : customers) {
            if (customer.getCustomerStatus()
                        .equals(Status.ACTIVE)) {
                customer.setCustomerStatus(Status.DISABLED);
            } else {
                customer.setCustomerStatus(Status.ACTIVE);
            }
            customerRepository.save(customer);
        }
        return customerConverter.entityToDto(customers);
    }

}
