package by.itechart.retailers.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "write_off_act")
public class WriteOffAct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "act_number")
    private Integer writeOffActNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "write_off_act_id")
    private List<WriteOffActRecord> writeOffActRecords;

    @Column(name = "act_date_time")
    private LocalDateTime actDateTime;

    @Column(name = "total_product_amount")
    private Integer totalProductAmount;

    @Column(name = "total_product_sum")
    private BigDecimal totalProductSum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
}
