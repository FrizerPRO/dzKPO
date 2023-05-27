package kpo.dz2.repos;

import kpo.dz2.domain.Dish;
import kpo.dz2.domain.Order;
import kpo.dz2.domain.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDishRepository extends JpaRepository<OrderDish, Integer> {

    OrderDish findFirstByDish(Dish dish);

    OrderDish findFirstByOrder(Order order);

    boolean existsByDishId(Integer id);

}
