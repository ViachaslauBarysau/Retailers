package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.entity.Customer;
import by.itechart.retailers.entity.Status;
import by.itechart.retailers.exceptions.NotUniqueDataException;
import by.itechart.retailers.repository.CustomerRepository;
import by.itechart.retailers.service.interfaces.CustomerService;
import by.itechart.retailers.service.interfaces.SendingCredentialsService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final UserService userService;
    private final SendingCredentialsService sendingCredentialsService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerConverter customerConverter, UserService userService, SendingCredentialsService sendingCredentialsService) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
        this.userService = userService;
        this.sendingCredentialsService = sendingCredentialsService;
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
    public CustomerDto create(CustomerDto customerDto) throws NotUniqueDataException {
        Customer customer = customerConverter.dtoToEntity(customerDto);
        if(emailExists(customer.getEmail())){
            throw new NotUniqueDataException("Email should be unique");
        }
        Customer persistsCustomer = customerRepository.save(customer);
        userService.create(customerConverter.entityToDto(persistsCustomer));
        return customerConverter.entityToDto(persistsCustomer);
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Customer customer = customerConverter.dtoToEntity(customerDto);
        Customer persistCustomer = customerRepository.findById(customer.getId())
                                                     .orElse(new Customer());

        persistCustomer.setName(customer.getName());
        persistCustomer.setRegistrationDate(customer.getRegistrationDate());
        persistCustomer.setCustomerStatus(customer.getCustomerStatus());
        persistCustomer=customerRepository.save(persistCustomer);

        return customerConverter.entityToDto(persistCustomer);
    }

    @Override
    public List<CustomerDto> updateStatus(List<Long> customerIds) {
         List<Customer> customers=customerRepository.findAllById(customerIds);
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

    @Override
    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }

}
