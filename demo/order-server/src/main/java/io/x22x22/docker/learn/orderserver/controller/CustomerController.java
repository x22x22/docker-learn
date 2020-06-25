package io.x22x22.docker.learn.orderserver.controller;

import io.x22x22.docker.learn.orderserver.Entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import service.CustomerService;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("customer")
    public Flux<Customer> getCustomerAll() {
        return customerService.getCustomerAll();
    }
}
