package service;

import io.x22x22.docker.learn.orderserver.Entities.Customer;
import io.x22x22.docker.learn.orderserver.repository.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author kdump
 */
@Component
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Flux<Customer> getCustomerAll() {
        return repository.findAll();
    }
}
