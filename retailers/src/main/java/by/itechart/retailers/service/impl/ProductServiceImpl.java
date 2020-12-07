package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.ProductConverter;
import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.*;
import by.itechart.retailers.service.interfaces.ProductService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final ApplicationRecordRepository applicationRecordRepository;
    private final LocationProductRepository locationProductRepository;
    private final InnerApplicationRepository innerApplicationRepository;
    private final SupplierApplicationRepository supplierApplicationRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter, ApplicationRecordRepository applicationRecordRepository, LocationProductRepository locationProductRepository, InnerApplicationRepository innerApplicationRepository, SupplierApplicationRepository supplierApplicationRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.applicationRecordRepository = applicationRecordRepository;
        this.locationProductRepository = locationProductRepository;
        this.innerApplicationRepository = innerApplicationRepository;
        this.supplierApplicationRepository = supplierApplicationRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ProductDto findById(long productId) {
        logger.info("Find by id {}", productId);
        Product product = productRepository.findById(productId)
                                           .orElse(new Product());

        return productConverter.entityToDto(product);
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        logger.info("Find all");
        UserDto userDto = userService.getUser();
        Page<Product> productPage = productRepository.findAllByCustomer_IdAndStatus(pageable, userDto.getCustomer()
                                                                                                     .getId(), DeletedStatus.ACTIVE);
        List<ProductDto> productDtos = productConverter.entityToDto(productPage.getContent());
        return new PageImpl<>(productDtos, pageable, productPage.getTotalElements());

    }

    @Override
    public ProductDto create(ProductDto productDto) throws BusinessException {
        logger.info("Create");
        Product product = productConverter.dtoToEntity(productDto);
        if (upcExists(product.getUpc())) {
            logger.error("Not unique upc {}", product.getUpc());
            throw new BusinessException("Upc should be unique");
        }
        Category category = findCategory(product);
        product.setCategory(category);
        Product persistProduct = productRepository.save(product);

        return productConverter.entityToDto(persistProduct);
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        logger.info("Update");
        Product product = productConverter.dtoToEntity(productDto);

        Product persistProduct = productRepository.findById(product.getId())
                                                  .orElse(new Product());
        if(!product.getUpc().equals(persistProduct.getUpc())) {
            if (upcExists(product.getUpc())) {
                logger.error("Not unique upc {}", product.getUpc());
                throw new BusinessException("Upc should be unique");
            }
        }
        Category category = findCategory(product);
        persistProduct.setCategory(category);
        persistProduct.setLabel(product.getLabel());
        persistProduct.setUpc(product.getUpc());
        persistProduct.setVolume(product.getVolume());
        persistProduct.setCustomer(product.getCustomer());
        persistProduct = productRepository.save(persistProduct);

        return productConverter.entityToDto(persistProduct);
    }

    private Category findCategory(Product product) {
        logger.info("Find category");
        Category category = categoryRepository.findByNameAndCustomer_Id(product.getCategory()
                                                                               .getName(), product.getCustomer()
                                                                                                  .getId())
                                              .get();
        if (category == null) {
            category = new Category();
            category.setCustomer(product.getCustomer());
            category.setName(product.getCategory()
                                    .getName());
            category.setCategoryTax(BigDecimal.valueOf(0));
        }
        return category;
    }

    @Override
    public List<ProductDto> delete(List<Long> productIds) {
        logger.info("Delete");
        List<Product> products=productRepository.findAllById(productIds);
        List<Product> undeletedProducts=new ArrayList<>(products);
        for(Product product:products){
            List<ApplicationRecord> applicationRecords = applicationRecordRepository.findAllByProduct(product);
            List<LocationProduct> locationProducts = locationProductRepository.findAllByProduct(product);
            List<SupplierApplication> supplierApplications = supplierApplicationRepository.findAllByRecordsListIn(applicationRecords);
            List<InnerApplication> innerApplications = innerApplicationRepository.findAllByRecordsListIn(applicationRecords);

            long locationProductCount = locationProducts.stream()
                                                        .filter(locationProduct -> (locationProduct.getAmount() != 0))
                                                        .count();
            if (locationProductCount > 0) {
                continue;
            }

            long supplierApplicationCount = supplierApplications.stream()
                                                                .filter(supplierApplication -> (supplierApplication.getApplicationStatus()
                                                                                                                   .equals(ApplicationStatus.OPEN)))
                                                                .count();
            if (supplierApplicationCount > 0) {
                continue;
            }

            long innerApplicationCount = innerApplications.stream()
                                                          .filter(innerApplication -> (innerApplication.getApplicationStatus()
                                                                                                       .equals(ApplicationStatus.OPEN)))
                                                          .count();
            if (innerApplicationCount > 0) {
                continue;
            }

            product.setStatus(DeletedStatus.DELETED);
            productRepository.save(product);
            undeletedProducts.remove(product);
           // products.remove(product);

        }
        return productConverter.entityToDto(undeletedProducts);
    }

    @Override
    public boolean upcExists(Integer upc) {
        logger.info("Check for existing upc {}", upc);
        UserDto userDto = userService.getUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        return productRepository.findAllByUpcAndCustomer_IdAndStatus(upc, customerId, DeletedStatus.ACTIVE)
                                .size() != 0;
    }


}


