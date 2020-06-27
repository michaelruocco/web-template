package uk.co.mruoc.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import uk.co.mruoc.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static uk.co.mruoc.model.Customer.CustomerBuilder;

public class MysqlCustomerRepository implements CustomerRepository {

    private final JdbcTemplate template;

    public MysqlCustomerRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Customer> getCustomers(int limit, int offset) {
        String query = "select id, firstName, surname, balance from customer limit ? offset ?;";
        Object[] arguments = { limit, offset };
        return template.query(query, arguments, new CustomerRowMapper());
    }

    @Override
    public Optional<Customer> getCustomer(String id) {
        String query = String.format("select id, firstName, surname, balance from customer where id = %s;", id);
        List<Customer> customers = template.query(query, new CustomerRowMapper());
        if (customers.size() < 1) {
            return Optional.empty();
        }
        return Optional.of(customers.get(0));
    }

    @Override
    public int getNumberOfCustomers() {
        return template.queryForObject("select count(*) from customer;", Integer.class);
    }

    @Override
    public void create(Customer customer) {
        String query = "insert into customer (firstName, surname, balance, id) values (?,?,?,?);";
        CustomerArgumentMapper mapper = new CustomerArgumentMapper(customer);
        template.update(query, mapper.getArgs());
    }

    @Override
    public void update(Customer customer) {
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

    public static class CustomerRowMapper implements RowMapper<Customer> {

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

    public static class CustomerArgumentMapper {

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