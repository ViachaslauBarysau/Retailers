package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.StateConverter;
import by.itechart.retailers.dto.StateDto;
import by.itechart.retailers.entity.State;
import by.itechart.retailers.repository.StateRepository;
import by.itechart.retailers.service.interfaces.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;
    private final StateConverter stateConverter;

    @Autowired
    public StateServiceImpl(StateRepository stateRepository, StateConverter stateConverter) {
        this.stateRepository = stateRepository;
        this.stateConverter = stateConverter;
    }

    @Override
    public StateDto findById(long stateID) {
        State state = stateRepository.findById(stateID).orElse(new State());

        return stateConverter.entityToDto(state);
    }

    @Override
    public List<StateDto> findAll(Pageable pageable) {
        Page<State> statePage = stateRepository.findAll(pageable);

        return stateConverter.entityToDto(statePage.toList());
    }

    @Override
    public StateDto create(StateDto stateDto) {
        State state = stateConverter.dtoToEntity(stateDto);
        State persistState = stateRepository.save(state);

        return stateConverter.entityToDto(persistState);
    }

    @Override
    public StateDto update(StateDto stateDto) {
        State state = stateConverter.dtoToEntity(stateDto);
        State persistState = stateRepository.findById(state.getId()).orElse(new State());

        persistState.setName(state.getName());
        persistState.setStateTax(state.getStateTax());

        return stateConverter.entityToDto(persistState);
    }
}
