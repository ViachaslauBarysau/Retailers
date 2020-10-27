package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.WriteOffActRecordConverter;
import by.itechart.retailers.dto.WriteOffActRecordDto;
import by.itechart.retailers.entity.WriteOffActRecord;
import by.itechart.retailers.repository.WriteOffActRecordRepository;
import by.itechart.retailers.service.WriteOffActRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriteOffActRecordServiceImpl implements WriteOffActRecordService {

    private WriteOffActRecordRepository writeOffActRecordRepository;
    private WriteOffActRecordConverter writeOffActRecordConverter;

    @Autowired
    public WriteOffActRecordServiceImpl(WriteOffActRecordRepository writeOffActRecordRepository,
                                        WriteOffActRecordConverter writeOffActRecordConverter) {
        this.writeOffActRecordRepository = writeOffActRecordRepository;
        this.writeOffActRecordConverter = writeOffActRecordConverter;
    }

    @Override
    public WriteOffActRecordDto findById(long writeOffActProductRecordId) {
        WriteOffActRecord writeOffActRecord = writeOffActRecordRepository
                .findById(writeOffActProductRecordId)
                .orElse(new WriteOffActRecord());

        return writeOffActRecordConverter.entityToDto(writeOffActRecord);
    }

    @Override
    public List<WriteOffActRecordDto> findAll() {
        List<WriteOffActRecord> writeOffActRecords = writeOffActRecordRepository.findAll();

        return writeOffActRecordConverter.entityToDto(writeOffActRecords);
    }

    @Override
    public WriteOffActRecordDto create(WriteOffActRecordDto writeOffActRecordDto) {
        WriteOffActRecord productRecord = writeOffActRecordConverter.dtoToEntity(writeOffActRecordDto);
        WriteOffActRecord persistRecord = writeOffActRecordRepository.save(productRecord);

        return writeOffActRecordConverter.entityToDto(persistRecord);
    }

    @Override
    public WriteOffActRecordDto update(WriteOffActRecordDto writeOffActRecordDto) {
        WriteOffActRecord productRecord = writeOffActRecordConverter.dtoToEntity(writeOffActRecordDto);
        WriteOffActRecord writeOffActRecord = writeOffActRecordRepository
                .findById(productRecord.getId())
                .orElse(new WriteOffActRecord());

        writeOffActRecord.setAmount(productRecord.getAmount());
        writeOffActRecord.setProduct(productRecord.getProduct());
        writeOffActRecord.setReason(productRecord.getReason());

        return writeOffActRecordConverter.entityToDto(writeOffActRecord);
    }
}