package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.WriteOffActItemRecordDto;
import by.itechart.retailers.service.WriteOffActItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriteOffActItemRecordServiceImpl implements WriteOffActItemRecordService {

    private WriteOffActItemRecordRepository writeOffActItemRecordRepository;
    private CustomerConverter customerConverter;

    @Autowired
    public WriteOffActItemRecordServiceImpl(WriteOffActItemRecordRepository writeOffActItemRecordRepository, CustomerConverter customerConverter) {
        this.writeOffActItemRecordRepository = writeOffActItemRecordRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public WriteOffActItemRecordDto findById(long writeOffActItemRecordId) {
        return null;
    }

    @Override
    public List<WriteOffActItemRecordDto> findAll() {
        return null;
    }

    @Override
    public WriteOffActItemRecordDto create(WriteOffActItemRecordDto writeOffActItemRecordDto) {
        return null;
    }

    @Override
    public WriteOffActItemRecordDto update(WriteOffActItemRecordDto writeOffActItemRecordDto) {
        return null;
    }
}
