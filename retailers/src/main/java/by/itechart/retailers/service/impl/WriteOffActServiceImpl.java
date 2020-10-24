package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.WriteOffActConverter;
import by.itechart.retailers.dto.WriteOffActDto;
import by.itechart.retailers.entity.WriteOffAct;
import by.itechart.retailers.repository.WriteOffActRepository;
import by.itechart.retailers.service.WriteOffActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriteOffActServiceImpl implements WriteOffActService {

    private WriteOffActRepository writeOffActRepository;
    private WriteOffActConverter converter;

    @Autowired
    public WriteOffActServiceImpl(WriteOffActRepository writeOffActRepository,
                                  WriteOffActConverter converter) {
        this.writeOffActRepository = writeOffActRepository;
        this.converter = converter;
    }

    @Override
    public WriteOffActDto findById(long writeOffActId) {
        WriteOffAct writeOffAct = writeOffActRepository.findById(writeOffActId).orElse(new WriteOffAct());

        return converter.entityToDto(writeOffAct);
    }

    @Override
    public List<WriteOffActDto> findAll() {
        List<WriteOffAct> writeOffActList = writeOffActRepository.findAll();

        return converter.entityToDto(writeOffActList);
    }

    @Override
    public WriteOffActDto create(WriteOffActDto writeOffActDto) {
        WriteOffAct writeOffAct = converter.dtoToEntity(writeOffActDto);
        WriteOffAct persistWriteOffAct = writeOffActRepository.save(writeOffAct);

        return converter.entityToDto(persistWriteOffAct);
    }

    @Override
    public WriteOffActDto update(WriteOffActDto writeOffActDto) {
        WriteOffAct writeOffAct = converter.dtoToEntity(writeOffActDto);
        WriteOffAct persistWriteOffAct = writeOffActRepository
                .findById(writeOffAct.getId())
                .orElse(new WriteOffAct());

        persistWriteOffAct.setActDateTime(writeOffAct.getActDateTime());
        persistWriteOffAct.setTotalItemAmount(writeOffAct.getTotalItemAmount());
        persistWriteOffAct.setWriteOffActItemRecords(writeOffAct.getWriteOffActItemRecords());

        return converter.entityToDto(persistWriteOffAct);
    }
}
