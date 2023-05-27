package kpo.dz2.service;

import kpo.dz2.domain.Order;
import kpo.dz2.repos.OrderRepository;

import java.time.LocalDateTime;
import java.util.Random;

public class UpdateOrderThread extends Thread {
    private OrderRepository orderRepository;
    private Integer orderId;

    public UpdateOrderThread(OrderRepository orderRepository, Integer orderId) {
        this.orderRepository = orderRepository;
        this.orderId = orderId;
    }

    @Override
    public void run() {
        try {
            // Generate a random duration between 3 to 10 seconds
            Random random = new Random();
            int duration = random.nextInt(8) + 3;
            Thread.sleep(duration * 1000); // Convert seconds to milliseconds
            // Call the updateOrderFieldsById method with the specified parameters
            //JOIN WITH MAIN HERE
            String status = "ready";

            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

            // Update the status and updatedAt fields
            order.setStatus(status);

            // Save the updated order
            orderRepository.save(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
