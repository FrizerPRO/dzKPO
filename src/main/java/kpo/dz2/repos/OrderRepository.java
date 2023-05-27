package kpo.dz2.repos;

import kpo.dz2.domain.Order;
import kpo.dz2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByUser(User user);
    Optional<Order> findById(Integer id);

    // Update order by ID
    @Modifying
    @Query("UPDATE Order o SET o.updatedAt = :updatedAt, o.status = :status WHERE o.id = :id")
    void updateOrderFieldsById(@Param(value = "id") Integer id, @Param(value = "updatedAt") LocalDateTime updatedAt, @Param(value = "status") String status);
}