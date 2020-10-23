package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.AddressConverter;
import by.itechart.retailers.dto.BillItemRecordDto;
import by.itechart.retailers.service.BillItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillItemRecordServiceImpl implements BillItemRecordService {

    private BillItemRecordRepository billItemRecordRepository;
    private AddressConverter addressConverter;

    @Autowired
    public BillItemRecordServiceImpl(BillItemRecordRepository billItemRecordRepository, AddressConverter addressConverter) {
        this.billItemRecordRepository = billItemRecordRepository;
        this.addressConverter = addressConverter;
    }

    @Override
    public BillItemRecordDto findById(long billItemRecordId) {
        return null;
    }

    @Override
    public List<BillItemRecordDto> findAll() {
        return null;
    }

    @Override
    public BillItemRecordDto create(BillItemRecordDto billItemRecordDto) {
        return null;
    }

    @Override
    public BillItemRecordDto update(BillItemRecordDto billItemRecordDto) {
        return null;
    }
}
