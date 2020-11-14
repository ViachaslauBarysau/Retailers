package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.BillDto;

import java.util.List;

public interface BillService {
    BillDto findById(long billId);

    List<BillDto> findAll();

    BillDto create(BillDto billDto);

    BillDto update(BillDto billDto);
}
