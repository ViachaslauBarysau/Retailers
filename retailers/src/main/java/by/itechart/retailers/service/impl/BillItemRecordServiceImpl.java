package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.BillItemRecordConverter;
import by.itechart.retailers.dto.BillItemRecordDto;
import by.itechart.retailers.entity.BillItemRecord;
import by.itechart.retailers.repository.BillItemRecordRepository;
import by.itechart.retailers.service.BillItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillItemRecordServiceImpl implements BillItemRecordService {

    private BillItemRecordRepository billItemRecordRepository;
    private BillItemRecordConverter billItemRecordConverter;

    @Autowired
    public BillItemRecordServiceImpl(BillItemRecordRepository billItemRecordRepository,
                                     BillItemRecordConverter billItemRecordConverter) {
        this.billItemRecordRepository = billItemRecordRepository;
        this.billItemRecordConverter = billItemRecordConverter;
    }

    @Override
    public BillItemRecordDto findById(long billItemRecordId) {
        BillItemRecord billItemRecord = billItemRecordRepository.findById(billItemRecordId)
                .orElse(new BillItemRecord());

        return billItemRecordConverter.entityToDto(billItemRecord);
    }

    @Override
    public List<BillItemRecordDto> findAll() {
        List<BillItemRecord> billList = billItemRecordRepository.findAll();
        return billItemRecordConverter.entityToDto(billList);
    }

    @Override
    public BillItemRecordDto create(BillItemRecordDto billItemRecordDto) {
        BillItemRecord billItemRecord = billItemRecordConverter.dtoToEntity(billItemRecordDto);
        BillItemRecord persistBillItem = billItemRecordRepository.save(billItemRecord);
        return billItemRecordConverter.entityToDto(persistBillItem);
    }

    @Override
    public BillItemRecordDto update(BillItemRecordDto billItemRecordDto) {
        BillItemRecord billItemRecord = billItemRecordConverter.dtoToEntity(billItemRecordDto);
        BillItemRecord persistBillItem = billItemRecordRepository.findById(billItemRecord.getId())
                .orElse(new BillItemRecord());

        persistBillItem.setItem(billItemRecord.getItem());
        persistBillItem.setItemAmount(billItemRecord.getItemAmount());
        persistBillItem.setItemPrice(billItemRecord.getItemPrice());

        return billItemRecordConverter.entityToDto(persistBillItem);
    }
}
