package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.StateDto;
import by.itechart.retailers.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    private StateRepository stateRepository;
    private CustomerConverter customerConverter;

    @Autowired
    public StateServiceImpl(StateRepository stateRepository, CustomerConverter customerConverter) {
        this.stateRepository = stateRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public StateDto findById(long stateID) {
        return null;
    }

    @Override
    public List<StateDto> findAll() {
        return null;
    }

    @Override
    public StateDto create(StateDto stateDto) {
        return null;
    }

    @Override
    public StateDto update(StateDto stateDto) {
        return null;
    }
}
