package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.BillConverter;
import by.itechart.retailers.converter.UserConverter;
import by.itechart.retailers.dto.BillDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Bill;
import by.itechart.retailers.entity.BillRecord;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.LocationProduct;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.BillRepository;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.repository.LocationRepository;
import by.itechart.retailers.service.interfaces.BillService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillConverter billConverter;
    private final LocationProductRepository locationProductRepository;
    private final UserService userService;
    private final LocationRepository locationRepository;
    private final UserConverter userConverter;
    Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    public BillServiceImpl(BillRepository billRepository, BillConverter billConverter, LocationProductRepository locationProductRepository, UserService userService, LocationRepository locationRepository, UserConverter userConverter) {
        this.billRepository = billRepository;
        this.billConverter = billConverter;
        this.locationProductRepository = locationProductRepository;
        this.userService = userService;
        this.locationRepository = locationRepository;
        this.userConverter = userConverter;
    }


    @Override
    public BillDto findById(long billId) {
        logger.info("Find by id: {}", billId);
        Bill bill = billRepository.findById(billId)
                                  .orElse(new Bill());

        return billConverter.entityToDto(bill);
    }

    @Override
    public Page<BillDto> findAll(Pageable pageable) {
        logger.info("Find all");
        UserDto userDto = userService.getUser();
        List<Location> locations = locationRepository.findAllByCustomer_Id(userDto.getCustomer()
                                                                                  .getId());
        Page<Bill> billPage = billRepository.findAllByLocationIn(pageable, locations);
        List<BillDto> billDtos = billConverter.entityToDto(billPage.getContent());
        return new PageImpl<>(billDtos, pageable, billPage.getTotalElements());
    }

    @Override
    @Transactional
    public BillDto create(BillDto billDto) throws BusinessException {
        logger.info("Create");
        Bill bill = billConverter.dtoToEntity(billDto);
        if (billNumberExists(bill.getBillNumber())) {
            throw new BusinessException("Bill number should be unique");
        }
        Location location = bill.getLocation();
        List<BillRecord> billRecords = bill.getRecordList();
        for (BillRecord billRecord : billRecords) {
            Long productId = billRecord.getProduct()
                                       .getId();
            LocationProduct locationProduct = locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), productId);

            if (billRecord.getProductAmount() > locationProduct.getAmount()) {
                logger.error("Not enough product {} in location", locationProduct.getProduct()
                                                                                 .getId());
                throw new BusinessException("Not enough amount of " + locationProduct.getProduct()
                                                                                     .getLabel() + " in location ");
            }
            Integer availableCapacity = location.getAvailableCapacity();
            location.setAvailableCapacity(availableCapacity - billRecord.getProduct()
                                                                        .getVolume() * billRecord.getProductAmount());
            Integer amount = locationProduct.getAmount();
            locationProduct.setAmount(amount - billRecord.getProductAmount());
            locationProductRepository.save(locationProduct);

            bill.setLocation(location);
        }
        Bill persistBill = billRepository.save(bill);
        return billConverter.entityToDto(persistBill);
    }

    @Override
    public boolean billNumberExists(Integer billNumber) {
        logger.info("Check for existing number {}", billNumber);
        UserDto userDto = userService.getUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        return billRepository.findAllByBillNumberAndCustomer_Id(billNumber, customerId)
                             .size() != 0;
    }
}
