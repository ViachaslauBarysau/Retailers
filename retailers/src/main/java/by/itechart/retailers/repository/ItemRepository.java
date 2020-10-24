package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
