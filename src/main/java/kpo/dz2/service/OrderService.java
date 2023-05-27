package kpo.dz2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kpo.dz2.domain.Order;
import kpo.dz2.domain.OrderDish;
import kpo.dz2.domain.User;
import kpo.dz2.model.OrderDTO;
import kpo.dz2.repos.OrderDishRepository;
import kpo.dz2.repos.OrderRepository;
import kpo.dz2.repos.UserRepository;
import kpo.dz2.util.NotFoundException;
import kpo.dz2.util.WebUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDishRepository orderDishRepository;

    public OrderService(final OrderRepository orderRepository, final UserRepository userRepository,
            final OrderDishRepository orderDishRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderDishRepository = orderDishRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map((order) -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Integer id) {
        return orderRepository.findById(id)
                .map((order) -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        order.setStatus("Cooking");
        int id = orderRepository.save(order).getId();
        UpdateOrderThread updateOrderThread = new UpdateOrderThread(orderRepository, id);
        updateOrderThread.start();
        return id;
    }

    public void update(final Integer id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Integer id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setSpecialRequest(order.getSpecialRequest());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setUpdatedAt(order.getUpdatedAt());
        orderDTO.setUser(order.getUser() == null ? null : order.getUser().getId());
        orderDTO.setDishSet(order.getOrderDishs().stream().map(OrderDish::getId).toList());
        return orderDTO;
    }

    public Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setStatus(orderDTO.getStatus());
        order.setSpecialRequest(orderDTO.getSpecialRequest());
        order.setCreatedAt(orderDTO.getCreatedAt());
        order.setUpdatedAt(orderDTO.getUpdatedAt());
        final User user = orderDTO.getUser() == null ? null : userRepository.findById(orderDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        order.setUser(user);
        order.setOrderDishs(orderDTO.getDishSet()
                .stream()
                .map(id -> orderDishRepository.findById(id).orElseThrow(() -> new NotFoundException("order dish cannot be found")))
                .collect(Collectors.toSet()));
        return order;
    }

    public String getReferencedWarning(final Integer id) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final OrderDish orderOrderDish = orderDishRepository.findFirstByOrder(order);
        if (orderOrderDish != null) {
            return WebUtils.getMessage("order.orderDish.order.referenced", orderOrderDish.getId());
        }
        return null;
    }

}
