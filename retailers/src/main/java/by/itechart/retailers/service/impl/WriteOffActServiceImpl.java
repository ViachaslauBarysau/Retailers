package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.WriteOffActDto;
import by.itechart.retailers.service.WriteOffActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriteOffActServiceImpl implements WriteOffActService {

    private WriteOffActRepository writeOffActRepository;
    private CustomerConverter customerConverter;

    @Autowired
    public WriteOffActServiceImpl(WriteOffActRepository writeOffActRepository, CustomerConverter customerConverter) {
        this.writeOffActRepository = writeOffActRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public WriteOffActDto findById(long writeOffActId) {
        return null;
    }

    @Override
    public List<WriteOffActDto> findAll() {
        return null;
    }

    @Override
    public WriteOffActDto create(WriteOffActDto writeOffActDto) {
        return null;
    }

    @Override
    public WriteOffActDto update(WriteOffActDto writeOffActDto) {
        return null;
    }
}
