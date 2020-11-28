package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.BillDto;
import by.itechart.retailers.exceptions.NotUniqueDataException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillService {
    BillDto findById(long billId);

    List<BillDto> findAll(Pageable pageable) throws NotUniqueDataException;

    BillDto create(BillDto billDto);

    boolean billNumberExists(Integer billNumber);
}
