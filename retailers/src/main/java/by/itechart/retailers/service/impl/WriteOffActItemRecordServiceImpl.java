package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.WriteOffActItemRecordConverter;
import by.itechart.retailers.dto.WriteOffActItemRecordDto;
import by.itechart.retailers.entity.WriteOffActItemRecord;
import by.itechart.retailers.repository.WriteOffActItemRecordRepository;
import by.itechart.retailers.service.WriteOffActItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriteOffActItemRecordServiceImpl implements WriteOffActItemRecordService {

    private WriteOffActItemRecordRepository writeOffActItemRecordRepository;
    private WriteOffActItemRecordConverter writeOffActItemRecordConverter;

    @Autowired
    public WriteOffActItemRecordServiceImpl(WriteOffActItemRecordRepository writeOffActItemRecordRepository,
                                            WriteOffActItemRecordConverter writeOffActItemRecordConverter) {
        this.writeOffActItemRecordRepository = writeOffActItemRecordRepository;
        this.writeOffActItemRecordConverter = writeOffActItemRecordConverter;
    }

    @Override
    public WriteOffActItemRecordDto findById(long writeOffActItemRecordId) {
        WriteOffActItemRecord writeOffActItemRecord = writeOffActItemRecordRepository
                .findById(writeOffActItemRecordId)
                .orElse(new WriteOffActItemRecord());

        return writeOffActItemRecordConverter.entityToDto(writeOffActItemRecord);
    }

    @Override
    public List<WriteOffActItemRecordDto> findAll() {
        List<WriteOffActItemRecord> writeOffActItemRecords = writeOffActItemRecordRepository.findAll();

        return writeOffActItemRecordConverter.entityToDto(writeOffActItemRecords);
    }

    @Override
    public WriteOffActItemRecordDto create(WriteOffActItemRecordDto writeOffActItemRecordDto) {
        WriteOffActItemRecord itemRecord = writeOffActItemRecordConverter.dtoToEntity(writeOffActItemRecordDto);
        WriteOffActItemRecord persistRecord = writeOffActItemRecordRepository.save(itemRecord);

        return writeOffActItemRecordConverter.entityToDto(persistRecord);
    }

    @Override
    public WriteOffActItemRecordDto update(WriteOffActItemRecordDto writeOffActItemRecordDto) {
        WriteOffActItemRecord itemRecord = writeOffActItemRecordConverter.dtoToEntity(writeOffActItemRecordDto);
        WriteOffActItemRecord writeOffActItemRecord = writeOffActItemRecordRepository
                .findById(itemRecord.getId())
                .orElse(new WriteOffActItemRecord());

        writeOffActItemRecord.setAmount(itemRecord.getAmount());
        writeOffActItemRecord.setItem(itemRecord.getItem());
        writeOffActItemRecord.setReason(itemRecord.getReason());

        return writeOffActItemRecordConverter.entityToDto(writeOffActItemRecord);
    }
}
