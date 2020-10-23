package by.itechart.retailers.service;

import by.itechart.retailers.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto findById(long itemId);

    List<ItemDto> findAll();

    ItemDto create(ItemDto itemDto);

    ItemDto update(ItemDto itemDto);
}
