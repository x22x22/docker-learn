package io.x22x22.docker.learn.orderserver.listener;

import io.r2dbc.spi.ConnectionFactory;
import io.x22x22.docker.learn.orderserver.service.InitDBService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ScriptStatementFailedException;
import org.springframework.stereotype.Component;

/**
 * @author Kdump
 */
@Log4j2
@Component
public class OrderServerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    InitDBService initializerService;

    @Qualifier("connectionFactory")
    @Autowired
    ConnectionFactory connectionFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            try {
                ConnectionFactoryInitializer initializer = initializerService.initializer(connectionFactory);
                initializer.afterPropertiesSet();
                initializer.destroy();
            } catch (ScriptStatementFailedException e) {
                if (e.getMessage().contains("Table 'order_master' already exists")) {
                    log.info(e.getMessage());
                } else {
                    throw e;
                }
            }
        }
    }
}
