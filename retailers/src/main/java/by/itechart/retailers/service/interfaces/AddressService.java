package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto findById(long addressId);

    List<AddressDto> findAll();

    AddressDto create(AddressDto addressDto);

    AddressDto update(AddressDto addressDto);
}
