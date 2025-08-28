package co.com.pragma.model.user.gateways;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface TransactionManager {
    <T> Mono<T> transactional(Mono<T> mono);
    <T> Flux<T> transactional(Flux<T> flux);
}
