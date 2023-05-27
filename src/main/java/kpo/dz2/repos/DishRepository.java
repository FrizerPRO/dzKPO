package kpo.dz2.repos;

import kpo.dz2.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DishRepository extends JpaRepository<Dish, Integer> {
}
