package by.itechart.retailers.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "supplier")
public class Supplier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "identifier")
    private String identifier;

    @OneToMany
    @JoinTable(name = "supplier_location",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = {@JoinColumn(name = "location_id")})
    private List<Location> wareHouseList;
}
