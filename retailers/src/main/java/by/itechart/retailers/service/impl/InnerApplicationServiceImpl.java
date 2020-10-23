package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.service.InnerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InnerApplicationServiceImpl implements InnerApplicationService {

    private InnerApplicationRepository innerApplicationRepository;
    private CustomerConverter customerConverter;

    @Autowired
    public InnerApplicationServiceImpl(InnerApplicationRepository innerApplicationRepository, CustomerConverter customerConverter) {
        this.innerApplicationRepository = innerApplicationRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public InnerApplicationDto findById(long innerApplicationId) {
        return null;
    }

    @Override
    public List<InnerApplicationDto> findAll() {
        return null;
    }

    @Override
    public InnerApplicationDto create(InnerApplicationDto innerApplicationDto) {
        return null;
    }

    @Override
    public InnerApplicationDto update(InnerApplicationDto innerApplicationDto) {
        return null;
    }
}
