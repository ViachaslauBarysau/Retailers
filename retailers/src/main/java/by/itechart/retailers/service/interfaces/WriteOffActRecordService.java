package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.WriteOffActRecordDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WriteOffActRecordService {
    WriteOffActRecordDto findById(long writeOffActProductRecordId);

    List<WriteOffActRecordDto> findAll(Pageable pageable);

    WriteOffActRecordDto create(WriteOffActRecordDto writeOffActRecordDto);

    WriteOffActRecordDto update(WriteOffActRecordDto writeOffActRecordDto);
}
