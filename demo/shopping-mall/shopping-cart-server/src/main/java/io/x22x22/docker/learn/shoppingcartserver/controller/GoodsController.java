package io.x22x22.docker.learn.shoppingcartserver.controller;

import io.x22x22.docker.learn.shoppingcartserver.entities.Goods;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author Kdump
 */
@RestController
public class GoodsController {
    private final ReactiveRedisOperations<String, Goods> goodsOps;

    GoodsController(ReactiveRedisOperations<String, Goods> goodsOps) {
        this.goodsOps = goodsOps;
    }

    @GetMapping("/goods")
    public Flux<Goods> all() {
        return goodsOps.keys("*")
            .flatMap(goodsOps.opsForValue()::get);
    }
}
