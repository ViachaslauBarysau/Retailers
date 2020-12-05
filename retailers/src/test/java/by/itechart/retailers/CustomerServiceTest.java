package by.itechart.retailers;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.entity.Customer;
import by.itechart.retailers.repository.CustomerRepository;
import by.itechart.retailers.service.impl.CustomerServiceImpl;
import by.itechart.retailers.service.interfaces.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    CustomerConverter customerConverter;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    UserService userService;
    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    public void findAllTest() {
        //given
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        List<CustomerDto> customerDtos = new ArrayList<>();
        customerDtos.add(new CustomerDto());
        PageRequest pageable = PageRequest.of(0, 1);
        Page<Customer> customerPage = new PageImpl<>(customers, pageable, 1);

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);
        when(customerConverter.entityToDto(customers)).thenReturn(customerDtos);
        //when
        customerService.findAll(pageable);
        //then
        verify(customerRepository).findAll(pageable);
        verify(customerConverter).entityToDto(customers);
    }
}
