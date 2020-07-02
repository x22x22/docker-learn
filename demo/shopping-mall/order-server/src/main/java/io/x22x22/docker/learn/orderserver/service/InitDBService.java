package io.x22x22.docker.learn.orderserver.service;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

/**
 * @author Kdump
 */
@Service
public class InitDBService {

    @Value("${spring.r2dbc.initializer.enabled}")
    private boolean initEnabled;

    public ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("sql/schema.sql")));
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("sql/order_master.sql")));
        initializer.setDatabasePopulator(populator);
        initializer.setEnabled(initEnabled);
        return initializer;
    }
}
