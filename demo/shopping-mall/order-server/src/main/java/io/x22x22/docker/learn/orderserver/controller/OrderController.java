package io.x22x22.docker.learn.orderserver.controller;

import io.x22x22.docker.learn.orderserver.entities.Order;
import io.x22x22.docker.learn.orderserver.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


/**
 * @author Kdump
 */
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("order")
    public Flux<Order> getOrderAll() {
        return orderService.getOrderAll();
    }
}
