package by.itechart.retailers.dto;

import by.itechart.retailers.entity.Location;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SupplierDto {
    private Long id;
    private String fullName;
    private String identifier;
    private List<Location> wareHouseList;
}
