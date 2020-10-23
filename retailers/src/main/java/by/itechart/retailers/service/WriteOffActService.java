package by.itechart.retailers.service;

import by.itechart.retailers.dto.WriteOffActDto;

import java.util.List;

public interface WriteOffActService {
    WriteOffActDto findById(long writeOffActId);

    List<WriteOffActDto> findAll();

    WriteOffActDto create(WriteOffActDto writeOffActDto);

    WriteOffActDto update(WriteOffActDto writeOffActDto);
}
