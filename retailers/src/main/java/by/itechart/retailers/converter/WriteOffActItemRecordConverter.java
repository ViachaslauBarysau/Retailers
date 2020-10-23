package by.itechart.retailers.converter;

import by.itechart.retailers.dto.WriteOffActItemRecordDto;
import by.itechart.retailers.entity.WriteOffActItemRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WriteOffActItemRecordConverter {
    public WriteOffActItemRecordDto entityToDto(WriteOffActItemRecord writeOffActItemRecord) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(writeOffActItemRecord, WriteOffActItemRecordDto.class);

    }

    public WriteOffActItemRecord dtoToEntity(WriteOffActItemRecordDto writeOffActItemRecordDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(writeOffActItemRecordDto, WriteOffActItemRecord.class);

    }

    public List<WriteOffActItemRecordDto> entityToDto(List<WriteOffActItemRecord> writeOffActItemRecords) {
        return writeOffActItemRecords.stream()
                                     .map(this::entityToDto)
                                     .collect(Collectors.toList());
    }

    public List<WriteOffActItemRecord> dtoToEntity(List<WriteOffActItemRecordDto> writeOffActItemRecordDtos) {
        return writeOffActItemRecordDtos.stream()
                                        .map(this::dtoToEntity)
                                        .collect(Collectors.toList());
    }
}
