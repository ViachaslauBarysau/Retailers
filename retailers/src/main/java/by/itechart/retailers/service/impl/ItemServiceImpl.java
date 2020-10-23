package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.dto.ItemDto;
import by.itechart.retailers.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private CustomerConverter customerConverter;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CustomerConverter customerConverter) {
        this.itemRepository = itemRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public ItemDto findById(long itemId) {
        return null;
    }

    @Override
    public List<ItemDto> findAll() {
        return null;
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        return null;
    }

    @Override
    public ItemDto update(ItemDto itemDto) {
        return null;
    }
}
