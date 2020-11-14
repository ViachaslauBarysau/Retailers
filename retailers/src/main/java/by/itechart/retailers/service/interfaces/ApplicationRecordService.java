package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.ApplicationRecordDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationRecordService {

    ApplicationRecordDto findById(long applicationProductRecordId);

    List<ApplicationRecordDto> findAll(Pageable pageable);

    ApplicationRecordDto create(ApplicationRecordDto applicationRecordDto);

    ApplicationRecordDto update(ApplicationRecordDto applicationRecordDto);

}
