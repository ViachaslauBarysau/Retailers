package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.StateDto;

import java.util.List;

public interface StateService {
    StateDto findById(long stateID);

    List<StateDto> findAll();

    StateDto create(StateDto stateDto);

    StateDto update(StateDto stateDto);
}
