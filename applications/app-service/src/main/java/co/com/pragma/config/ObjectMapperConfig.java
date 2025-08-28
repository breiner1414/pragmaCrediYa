package co.com.pragma.config;

import co.com.pragma.model.user.gateways.TransactionManager;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.UserUseCase;
import org.reactivecommons.utils.ObjectMapper;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapperImp();
    }

    @Bean
    public UserUseCase userUseCase(UserRepository userRepository,
                                   TransactionManager transactionManager) {
        return new UserUseCase(userRepository, transactionManager);
    }

}
