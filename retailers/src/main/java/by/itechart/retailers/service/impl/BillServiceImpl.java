package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.BillConverter;
import by.itechart.retailers.dto.BillDto;
import by.itechart.retailers.entity.Bill;
import by.itechart.retailers.repository.BillRepository;
import by.itechart.retailers.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private BillRepository billRepository;
    private BillConverter billConverter;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, BillConverter billConverter) {
        this.billRepository = billRepository;
        this.billConverter = billConverter;
    }

    @Override
    public BillDto findById(long billId) {
        Bill bill = billRepository.findById(billId).orElse(new Bill());

        return billConverter.entityToDto(bill);
    }

    @Override
    public List<BillDto> findAll() {
        List<Bill> billList = billRepository.findAll();
        return billConverter.entityToDto(billList);
    }

    @Override
    public BillDto create(BillDto billDto) {
        Bill bill = billConverter.dtoToEntity(billDto);
        Bill persistBill = billRepository.save(bill);
        return billConverter.entityToDto(persistBill);
    }

    @Override
    public BillDto update(BillDto billDto) {
        Bill bill = billConverter.dtoToEntity(billDto);
        Bill persistBill = billRepository.findById(bill.getId()).orElse(new Bill());

        persistBill.setLocation(bill.getLocation());
        persistBill.setBillNumber(bill.getBillNumber());
        persistBill.setRecordList(bill.getRecordList());
        persistBill.setRegistrationDateTime(bill.getRegistrationDateTime());
        persistBill.setShopManager(bill.getShopManager());
        persistBill.setTotalItemAmount(bill.getTotalItemAmount());
        persistBill.setTotalUnitNumber(bill.getTotalUnitNumber());

        return billConverter.entityToDto(persistBill);
    }
}
