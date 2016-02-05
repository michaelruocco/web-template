package uk.co.mruoc.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import uk.co.mruoc.exception.CustomerNotFoundException;
import uk.co.mruoc.exception.InvalidCustomerIdException;
import uk.co.mruoc.exception.CustomerIdAlreadyUsedException;
import uk.co.mruoc.model.Customer;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static uk.co.mruoc.model.Customer.CustomerBuilder;

public class MysqlCustomerService implements CustomerService {

    private final JdbcTemplate template;
    private final CustomerValidator validator;

    public MysqlCustomerService(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.validator = new CustomerValidator();
    }

    @Override
    public List<Customer> getCustomers() {
        return getCustomers(Integer.MAX_VALUE, 0);
    }

    @Override
    public List<Customer> getCustomers(int limit, int offset) {
        String query = "select id, firstName, surname, balance from customer limit ? offset ?;";
        Object[] arguments = { limit, offset };
        return template.query(query, arguments, new CustomerRowMapper());
    }

    @Override
    public Customer getCustomer(String id) {
        String query = String.format("select id, firstName, surname, balance from customer where id = %s;", id);
        List<Customer> customers = template.query(query, new CustomerRowMapper());
        if (customers.size() < 1)
            throw new CustomerNotFoundException(id);
        return customers.get(0);
    }

    @Override
    public int getNumberOfCustomers() {
        return template.queryForObject("select count(*) from customer;", Integer.class);
    }

    @Override
    public void create(Customer customer) {
        if (!validator.hasValidId(customer))
            throw new InvalidCustomerIdException(customer.getId());

        if (alreadyExists(customer.getId()))
            throw new CustomerIdAlreadyUsedException(customer.getId());

        String query = "insert into customer (firstName, surname, balance, id) values (?,?,?,?);";
        CustomerArgumentMapper mapper = new CustomerArgumentMapper(customer);
        template.update(query, mapper.getArgs());
    }

    @Override
    public void update(Customer customer) {
        if (!alreadyExists(customer.getId()))
            throw new CustomerNotFoundException(customer.getId());

        String query = "update customer set firstName = ?, surname = ?, balance = ? where id = ?;";
        CustomerArgumentMapper mapper = new CustomerArgumentMapper(customer);
        template.update(query, mapper.getArgs());
    }

    @Override
    public void delete(String id) {
        String query = "delete from customer where id = ?;";
        Object[] arguments = { id };
        template.update(query, arguments);
    }

    private boolean alreadyExists(String id) {
        String query = String.format("select count(*) from customer where id = ?;", id);
        int count = template.queryForObject(query, new Object[] { id }, Integer.class);
        return count > 0;
    }

    private static class CustomerRowMapper implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CustomerBuilder()
                    .setId(rs.getString("id"))
                    .setFirstName(rs.getString("firstName"))
                    .setSurname(rs.getString("surname"))
                    .setBalance(rs.getBigDecimal("balance"))
                    .build();
        }

    }

    private static class CustomerArgumentMapper {

        private final Customer customer;

        public CustomerArgumentMapper(Customer customer) {
            this.customer = customer;
        }

        public Object[] getArgs() {
            Object[] args = new Object[4];
            args[0] = customer.getFirstName();
            args[1] = customer.getSurname();
            args[2] = customer.getBalance();
            args[3] = customer.getId();
            return args;
        }

    }

}