package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.WriteOffActDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WriteOffActService {
    WriteOffActDto findById(long writeOffActId);

    List<WriteOffActDto> findAll(Pageable pageable);

    WriteOffActDto create(WriteOffActDto writeOffActDto);

    WriteOffActDto update(WriteOffActDto writeOffActDto);
}
