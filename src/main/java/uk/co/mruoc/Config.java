package uk.co.mruoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.co.mruoc.facade.CustomerFacade;
import uk.co.mruoc.repository.CustomerRepository;
import uk.co.mruoc.repository.MysqlCustomerRepository;
import uk.co.mruoc.service.CustomerService;

@Configuration
public class Config {

    @Bean
    public CustomerRepository customerRepository(JdbcTemplate template) {
        return new MysqlCustomerRepository(template);
    }

    @Bean
    public CustomerService customerService(CustomerRepository repository) {
        return new CustomerService(repository);
    }

    @Bean
    public CustomerFacade customerFacade(CustomerService service) {
        return new CustomerFacade(service);
    }

}