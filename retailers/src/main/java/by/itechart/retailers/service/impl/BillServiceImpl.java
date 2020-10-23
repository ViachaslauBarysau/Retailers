package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.AddressConverter;
import by.itechart.retailers.dto.BillDto;
import by.itechart.retailers.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private BillRepository billRepository;
    private AddressConverter addressConverter;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, AddressConverter addressConverter) {
        this.billRepository = billRepository;
        this.addressConverter = addressConverter;
    }

    @Override
    public BillDto findById(long billId) {
        return null;
    }

    @Override
    public List<BillDto> findAll() {
        return null;
    }

    @Override
    public BillDto create(BillDto billDto) {
        return null;
    }

    @Override
    public BillDto update(BillDto billDto) {
        return null;
    }
}
