package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.WriteOffActDto;
import by.itechart.retailers.exceptions.NotUniqueDataException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WriteOffActService {
    WriteOffActDto findById(long writeOffActId);

    List<WriteOffActDto> findAll(Pageable pageable);

    WriteOffActDto create(WriteOffActDto writeOffActDto) throws NotUniqueDataException;

    boolean writeOffActNumberExists(Integer writeOffActNumber);
}
