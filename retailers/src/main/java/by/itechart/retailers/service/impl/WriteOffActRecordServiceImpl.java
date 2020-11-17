package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.WriteOffActRecordConverter;
import by.itechart.retailers.dto.WriteOffActRecordDto;
import by.itechart.retailers.entity.WriteOffActRecord;
import by.itechart.retailers.repository.WriteOffActRecordRepository;
import by.itechart.retailers.service.interfaces.WriteOffActRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriteOffActRecordServiceImpl implements WriteOffActRecordService {

    private final WriteOffActRecordRepository writeOffActRecordRepository;
    private final WriteOffActRecordConverter writeOffActRecordConverter;

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
    public List<WriteOffActRecordDto> findAll(Pageable pageable) {
        Page<WriteOffActRecord> writeOffActRecordPage = writeOffActRecordRepository.findAll(pageable);

        return writeOffActRecordConverter.entityToDto(writeOffActRecordPage.toList());
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
        WriteOffActRecord persistWriteOffActRecord = writeOffActRecordRepository
                .findById(productRecord.getId())
                .orElse(new WriteOffActRecord());

        persistWriteOffActRecord.setAmount(productRecord.getAmount());
        persistWriteOffActRecord.setProduct(productRecord.getProduct());
        persistWriteOffActRecord.setReason(productRecord.getReason());
        persistWriteOffActRecord=writeOffActRecordRepository.save(persistWriteOffActRecord);

        return writeOffActRecordConverter.entityToDto(persistWriteOffActRecord);
    }
}
