package by.itechart.retailers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private Long id;
    private Integer upc;
    private String label;
    private CategoryDto categoryDto;
    private Integer volume;
}
