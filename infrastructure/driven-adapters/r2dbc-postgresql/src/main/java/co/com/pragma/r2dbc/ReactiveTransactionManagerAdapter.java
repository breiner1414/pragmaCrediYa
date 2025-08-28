package co.com.pragma.r2dbc;


import co.com.pragma.model.user.gateways.TransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public class ReactiveTransactionManagerAdapter implements TransactionManager {

    private final TransactionalOperator operator;

    public ReactiveTransactionManagerAdapter(TransactionalOperator operator) {
        this.operator = operator;
    }

    @Override
    public <T> Mono<T> transactional(Mono<T> mono) {
        return mono.as(operator::transactional);
    }

    @Override
    public <T> Flux<T> transactional(Flux<T> flux) {
        return flux.as(operator::transactional);
    }
}