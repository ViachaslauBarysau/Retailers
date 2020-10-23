package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.AddressConverter;
import by.itechart.retailers.dto.AddressDto;
import by.itechart.retailers.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;
    private AddressConverter addressConverter;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, AddressConverter addressConverter) {
        this.addressRepository = addressRepository;
        this.addressConverter = addressConverter;
    }

    @Override
    public AddressDto findById(long addressId) {
        return null;
    }

    @Override
    public List<AddressDto> findAll() {
        return null;
    }

    @Override
    public AddressDto create(AddressDto addressDto) {
        return null;
    }

    @Override
    public AddressDto update(AddressDto addressDto) {
        return null;
    }
}
