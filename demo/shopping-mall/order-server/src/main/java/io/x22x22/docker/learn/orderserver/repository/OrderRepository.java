package io.x22x22.docker.learn.orderserver.repository;

import io.x22x22.docker.learn.orderserver.entities.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kdump
 */
@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

}