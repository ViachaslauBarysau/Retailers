package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.ItemConverter;
import by.itechart.retailers.dto.ItemDto;
import by.itechart.retailers.entity.Item;
import by.itechart.retailers.repository.ItemRepository;
import by.itechart.retailers.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private ItemConverter itemConverter;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    public ItemDto findById(long itemId) {
        Item item = itemRepository.findById(itemId).orElse(new Item());

        return itemConverter.entityToDto(item);
    }

    @Override
    public List<ItemDto> findAll() {
        List<Item> itemList = itemRepository.findAll();

        return itemConverter.entityToDto(itemList);
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        Item item = itemConverter.dtoToEntity(itemDto);
        Item persistItem = itemRepository.save(item);

        return itemConverter.entityToDto(persistItem);
    }

    @Override
    public ItemDto update(ItemDto itemDto) {
        Item item = itemConverter.dtoToEntity(itemDto);
        Item persistItem = itemRepository.findById(item.getId()).orElse(new Item());

        persistItem.setCategory(item.getCategory());
        persistItem.setLabel(item.getLabel());
        persistItem.setUpc(item.getUpc());
        persistItem.setVolume(item.getVolume());

        return itemConverter.entityToDto(persistItem);
    }
}
