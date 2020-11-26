package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByCustomer_Id(Pageable pageable, Long customerId);

    Category findByNameAndCustomer_Id(String name, Long customerId);
}
