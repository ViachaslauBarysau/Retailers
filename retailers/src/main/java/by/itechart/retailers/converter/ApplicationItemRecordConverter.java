package by.itechart.retailers.converter;

import by.itechart.retailers.dto.ApplicationItemRecordDto;
import by.itechart.retailers.entity.ApplicationItemRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationItemRecordConverter {
    public ApplicationItemRecordDto entityToDto(ApplicationItemRecord applicationItemRecord) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(applicationItemRecord, ApplicationItemRecordDto.class);

    }

    public ApplicationItemRecord dtoToEntity(ApplicationItemRecordDto applicationItemRecordDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(applicationItemRecordDto, ApplicationItemRecord.class);

    }

    public List<ApplicationItemRecordDto> entityToDto(List<ApplicationItemRecord> applicationItemRecords) {
        return applicationItemRecords.stream()
                                     .map(this::entityToDto)
                                     .collect(Collectors.toList());
    }

    public List<ApplicationItemRecord> dtoToEntity(List<ApplicationItemRecordDto> applicationItemRecordDtos) {
        return applicationItemRecordDtos.stream()
                                        .map(this::dtoToEntity)
                                        .collect(Collectors.toList());
    }
}
