package io.x22x22.docker.learn.shoppingcartserver.loader;

import io.x22x22.docker.learn.shoppingcartserver.entities.Goods;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @author Kdump
 */
@Component
@Log4j2
public class GoodsLoader {
    @Autowired
    private ReactiveRedisConnectionFactory factory;

    @Autowired
    private ReactiveRedisOperations<String, Goods> goodsOps;

    @PostConstruct
    public void loadData() {
        factory.getReactiveConnection().serverCommands().flushAll()
            .thenMany(
                Flux.just("mazida-01", "bentian-01", "benchi-01")
                    .map(name -> new Goods(UUID.randomUUID().toString(), name))
                    .flatMap(goods -> goodsOps.opsForValue().set(goods.getId(), goods)))
            .thenMany(goodsOps.keys("*")
                .flatMap(goodsOps.opsForValue()::get))
            .subscribe(log::info);
    }
}
