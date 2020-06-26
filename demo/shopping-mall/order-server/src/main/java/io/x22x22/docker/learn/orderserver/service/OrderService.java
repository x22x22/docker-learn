package io.x22x22.docker.learn.orderserver.service;

import io.x22x22.docker.learn.orderserver.entities.Order;
import io.x22x22.docker.learn.orderserver.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author Kdump
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<Order> getOrderAll() {
        return orderRepository.findAll();
    }
}
