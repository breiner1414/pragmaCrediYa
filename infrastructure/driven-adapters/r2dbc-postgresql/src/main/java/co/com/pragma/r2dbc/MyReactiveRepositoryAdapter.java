package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.mapper.UserEntityMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User, UserEntity, Long, MyReactiveRepository
        > implements UserRepository {

    private final MyReactiveRepository repository;
    private final UserEntityMapper userEntityMapper;

    public MyReactiveRepositoryAdapter(MyReactiveRepository repository,
                                       ObjectMapper mapper,
                                       UserEntityMapper userEntityMapper) {
        super(repository, mapper, userEntityMapper::toDomain);
        this.repository = repository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public Mono<User> saveUser(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        return repository.save(entity)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(userEntityMapper::toDomain);
    }
}
