package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.BillConverter;
import by.itechart.retailers.dto.BillDto;
import by.itechart.retailers.entity.Bill;
import by.itechart.retailers.repository.BillRepository;
import by.itechart.retailers.service.interfaces.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillConverter billConverter;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, BillConverter billConverter) {
        this.billRepository = billRepository;
        this.billConverter = billConverter;
    }

    @Override
    public BillDto findById(long billId) {
        Bill bill = billRepository.findById(billId)
                                  .orElse(new Bill());

        return billConverter.entityToDto(bill);
    }

    @Override
    public List<BillDto> findAll(Pageable pageable) {
        Page<Bill> billPage = billRepository.findAll(pageable);
        return billConverter.entityToDto(billPage.toList());
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
        Bill persistBill = billRepository.findById(bill.getId())
                                         .orElse(new Bill());

        persistBill.setLocation(bill.getLocation());
        persistBill.setBillNumber(bill.getBillNumber());
        persistBill.setRecordList(bill.getRecordList());
        persistBill.setRegistrationDateTime(bill.getRegistrationDateTime());
        persistBill.setShopManager(bill.getShopManager());
        persistBill.setTotalProductAmount(bill.getTotalProductAmount());
        persistBill.setTotalUnitNumber(bill.getTotalUnitNumber());
        persistBill = billRepository.save(persistBill);

        return billConverter.entityToDto(persistBill);
    }
}
