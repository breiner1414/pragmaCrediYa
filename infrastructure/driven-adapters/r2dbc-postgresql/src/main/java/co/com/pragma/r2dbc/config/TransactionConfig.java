package co.com.pragma.r2dbc.config;

import co.com.pragma.model.user.gateways.TransactionManager;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.ReactiveTransactionManagerAdapter;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
public class TransactionConfig {
    @Bean
    public R2dbcTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    public TransactionalOperator transactionalOperator(ReactiveTransactionManager txManager) {
        return TransactionalOperator.create(txManager);
    }

    @Bean
    public TransactionManager transactionManagerPort(TransactionalOperator operator) {
        return new ReactiveTransactionManagerAdapter(operator);
    }

}
