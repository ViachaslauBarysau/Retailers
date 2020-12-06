package by.itechart.retailers;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.entity.Customer;
import by.itechart.retailers.entity.Status;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.CustomerRepository;
import by.itechart.retailers.service.impl.CustomerServiceImpl;
import by.itechart.retailers.service.impl.UserServiceImpl;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    CustomerConverter customerConverter;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    UserServiceImpl userService;
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

    @Test
    public void findByIdTest() {
        //given
        Customer customer = new Customer();
        CustomerDto customerDto = new CustomerDto();
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerConverter.entityToDto(customer)).thenReturn(customerDto);
        //when
        customerService.findById(customerId);
        //then
        verify(customerRepository).findById(customerId);
        verify(customerConverter).entityToDto(customer);
    }

    @Test(expected = BusinessException.class)
    public void createTestBusinessException() {
        //given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("string");

        Customer customer = new Customer();
        customer.setEmail("string");

        when(customerConverter.dtoToEntity(customerDto)).thenReturn(customer);
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        //when
        customerService.create(customerDto);
        //then
        verify(customerConverter).dtoToEntity(customerDto);
    }

    @Test
    public void createTest() {
        //given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("string");
        customerDto.setName("name");

        Customer customer = new Customer();
        customerDto.setEmail("string");
        customerDto.setName("name");

        when(customerConverter.dtoToEntity(customerDto)).thenReturn(customer);
        assertThat(customerRepository.findByEmail(customer.getEmail())).isEmpty();
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerConverter.entityToDto(customer)).thenReturn(customerDto);
        //when
        customerService.create(customerDto);
        //then
        verify(customerConverter).dtoToEntity(customerDto);
        verify(customerRepository).save(customer);
        verify(userService).create(customerDto);
    }

    @Test
    public void updateTest() {
        //given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail("string");
        customerDto.setName("name");

        Customer customer = new Customer();
        customerDto.setEmail("string");
        customerDto.setName("name");

        when(customerConverter.dtoToEntity(customerDto)).thenReturn(customer);
        when(customerRepository.findById(customerDto.getId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerConverter.entityToDto(customer)).thenReturn(customerDto);
        //when
        customerService.update(customerDto);
        //then
        verify(customerConverter).dtoToEntity(customerDto);
        verify(customerRepository).save(customer);
    }

    @Test
    public void updateStatusTest() {
        //given
        List<Long> customerIds = new ArrayList<Long>() {{
            add(1L);
            add(2L);
        }};
        Customer customer1 = new Customer();
        customer1.setCustomerStatus(Status.ACTIVE);
        Customer customer2 = new Customer();
        customer2.setCustomerStatus(Status.DISABLED);

        List<Customer> customers = new ArrayList<Customer>() {{
            add(customer1);
            add(customer2);
        }};
        when(customerRepository.findAllById(customerIds)).thenReturn(customers);
        //when
        customerService.updateStatus(customerIds);
        //then
        verify(customerRepository).findAllById(customerIds);
        verify(customerRepository).save(customer1);
        verify(customerConverter).entityToDto(customers);
    }
}
