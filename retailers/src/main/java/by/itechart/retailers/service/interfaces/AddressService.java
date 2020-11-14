package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.AddressDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    AddressDto findById(long addressId);

    List<AddressDto> findAll(Pageable pageable);

    AddressDto create(AddressDto addressDto);

    AddressDto update(AddressDto addressDto);
}
