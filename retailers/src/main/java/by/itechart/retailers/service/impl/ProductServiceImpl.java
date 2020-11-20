package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.ProductConverter;
import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.entity.Product;
import by.itechart.retailers.repository.ApplicationRecordRepository;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.repository.ProductRepository;
import by.itechart.retailers.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final ApplicationRecordRepository applicationRecordRepository;
    private final LocationProductRepository locationProductRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter, ApplicationRecordRepository applicationRecordRepository, LocationProductRepository locationProductRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.applicationRecordRepository = applicationRecordRepository;
        this.locationProductRepository = locationProductRepository;
    }

    @Override
    public ProductDto findById(long productId) {
        Product product = productRepository.findById(productId)
                                           .orElse(new Product());

        return productConverter.entityToDto(product);
    }

    @Override
    public List<ProductDto> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        return productConverter.entityToDto(productPage.toList());
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = productConverter.dtoToEntity(productDto);
        Product persistProduct = productRepository.save(product);

        return productConverter.entityToDto(persistProduct);
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Product product = productConverter.dtoToEntity(productDto);
        Product persistProduct = productRepository.findById(product.getId())
                                                  .orElse(new Product());

        persistProduct.setCategory(product.getCategory());
        persistProduct.setLabel(product.getLabel());
        persistProduct.setUpc(product.getUpc());
        persistProduct.setVolume(product.getVolume());
        persistProduct.setCustomer(product.getCustomer());
        persistProduct = productRepository.save(persistProduct);

        return productConverter.entityToDto(persistProduct);
    }

    @Override
    public List<ProductDto> delete(List<ProductDto> productDtos) {
        List<Product> products = productConverter.dtoToEntity(productDtos);
        for (Product product : products) {
            if (!applicationRecordRepository.existsByProduct(product)) {
                if (!locationProductRepository.existsByProduct(product)) {
                    productRepository.delete(product);
                    products.remove(product);
                } else {
                    if (locationProductRepository.findById(product.getId())
                                                 .get()
                                                 .getAmount() == 0) {
                        productRepository.delete(product);
                        products.remove(product);
                    }
                }

            }

        }
        return productConverter.entityToDto(products);
    }
}
       /* for (Product product : products) {
            if (!applicationRecordRepository.existsByProduct(product)
                    && !locationProductRepository.existsByProduct(product)) {
                productRepository.delete(product);
            }
            // тут проверять amount на ноль в location_product потому что может быть товар но количество равно 0
        }*/


