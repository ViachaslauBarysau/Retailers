package by.itechart.retailers.converter;

import by.itechart.retailers.dto.ItemDto;
import by.itechart.retailers.entity.Item;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemConverter {
    public ItemDto entityToDto(Item item) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(item, ItemDto.class);

    }

    public Item dtoToEntity(ItemDto itemDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(itemDto, Item.class);

    }

    public List<ItemDto> entityToDto(List<Item> items) {
        return items.stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());
    }

    public List<Item> dtoToEntity(List<ItemDto> itemDtos) {
        return itemDtos.stream()
                       .map(this::dtoToEntity)
                       .collect(Collectors.toList());
    }
}
