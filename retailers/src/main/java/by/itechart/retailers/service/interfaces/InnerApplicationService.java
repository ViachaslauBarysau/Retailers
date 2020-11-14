package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.InnerApplicationDto;

import java.util.List;

public interface InnerApplicationService {
    InnerApplicationDto findById(long innerApplicationId);

    List<InnerApplicationDto> findAll();

    InnerApplicationDto create(InnerApplicationDto innerApplicationDto);

    InnerApplicationDto update(InnerApplicationDto innerApplicationDto);
}
