package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InnerApplicationService {
    InnerApplicationDto findById(long innerApplicationId);

    List<InnerApplicationDto> findAll(Pageable pageable);

    InnerApplicationDto create(InnerApplicationDto innerApplicationDto);

    InnerApplicationDto update(InnerApplicationDto innerApplicationDto);

    InnerApplicationDto updateStatus(Long innerApplicationId) throws BusinessException;

    boolean applicationNumberExists(Integer applicationNumber);

}
