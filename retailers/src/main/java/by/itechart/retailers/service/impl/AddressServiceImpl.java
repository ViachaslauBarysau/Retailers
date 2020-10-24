package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.AddressConverter;
import by.itechart.retailers.dto.AddressDto;
import by.itechart.retailers.entity.Address;
import by.itechart.retailers.repository.AddressRepository;
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
        Address address = addressRepository.findById(addressId).orElse(new Address());
        return addressConverter.entityToDto(address);
    }

    @Override
    public List<AddressDto> findAll() {
        List<Address> addressList = addressRepository.findAll();
        return addressConverter.entityToDto(addressList);
    }

    @Override
    public AddressDto create(AddressDto addressDto) {
        Address address = addressConverter.dtoToEntity(addressDto);
        Address persistAddress = addressRepository.save(address);
        return addressConverter.entityToDto(persistAddress);
    }

    @Override
    public AddressDto update(AddressDto addressDto) {
        Address addressWithUpdatedFields = addressConverter.dtoToEntity(addressDto);

        Address persistAddress = addressRepository.findAddressByStateAndCity(addressWithUpdatedFields.getState(),
                addressWithUpdatedFields.getCity());

        persistAddress.setState(addressWithUpdatedFields.getState());
        persistAddress.setCity(addressWithUpdatedFields.getCity());
        persistAddress.setFirstAddressLine(addressWithUpdatedFields.getFirstAddressLine());
        persistAddress.setSecondAddressLine(addressWithUpdatedFields.getSecondAddressLine());

        return addressConverter.entityToDto(persistAddress);
    }
}
