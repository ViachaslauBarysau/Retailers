package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.BillRecordConverter;
import by.itechart.retailers.dto.BillIRecordDto;
import by.itechart.retailers.entity.BillRecord;
import by.itechart.retailers.repository.BillRecordRepository;
import by.itechart.retailers.service.BillRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillRecordServiceImpl implements BillRecordService {

    private BillRecordRepository billRecordRepository;
    private BillRecordConverter billRecordConverter;

    @Autowired
    public BillRecordServiceImpl(BillRecordRepository billRecordRepository,
                                 BillRecordConverter billRecordConverter) {
        this.billRecordRepository = billRecordRepository;
        this.billRecordConverter = billRecordConverter;
    }

    @Override
    public BillIRecordDto findById(long billProductRecordId) {
        BillRecord billRecord = billRecordRepository.findById(billProductRecordId)
                .orElse(new BillRecord());

        return billRecordConverter.entityToDto(billRecord);
    }

    @Override
    public List<BillIRecordDto> findAll() {
        List<BillRecord> billList = billRecordRepository.findAll();
        return billRecordConverter.entityToDto(billList);
    }

    @Override
    public BillIRecordDto create(BillIRecordDto billIRecordDto) {
        BillRecord billRecord = billRecordConverter.dtoToEntity(billIRecordDto);
        BillRecord persistBillProduct = billRecordRepository.save(billRecord);
        return billRecordConverter.entityToDto(persistBillProduct);
    }

    @Override
    public BillIRecordDto update(BillIRecordDto billIRecordDto) {
        BillRecord billRecord = billRecordConverter.dtoToEntity(billIRecordDto);
        BillRecord persistBillProduct = billRecordRepository.findById(billRecord.getId())
                .orElse(new BillRecord());

        persistBillProduct.setProduct(billRecord.getProduct());
        persistBillProduct.setProductAmount(billRecord.getProductAmount());
        persistBillProduct.setProductPrice(billRecord.getProductPrice());

        return billRecordConverter.entityToDto(persistBillProduct);
    }
}
