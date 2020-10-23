package by.itechart.retailers.converter;

import by.itechart.retailers.dto.BillItemRecordDto;
import by.itechart.retailers.entity.BillItemRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillItemRecordConverter {
    public BillItemRecordDto entityToDto(BillItemRecord billItemRecord) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(billItemRecord, BillItemRecordDto.class);

    }

    public BillItemRecord dtoToEntity(BillItemRecordDto billItemRecordDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(billItemRecordDto, BillItemRecord.class);

    }

    public List<BillItemRecordDto> entityToDto(List<BillItemRecord> billItemRecords) {
        return billItemRecords.stream()
                              .map(this::entityToDto)
                              .collect(Collectors.toList());
    }

    public List<BillItemRecord> dtoToEntity(List<BillItemRecordDto> billItemRecordDtos) {
        return billItemRecordDtos.stream()
                                 .map(this::dtoToEntity)
                                 .collect(Collectors.toList());
    }
}
