package uk.co.mruoc.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.co.mruoc.exception.CustomerNotFoundException;
import uk.co.mruoc.exception.InvalidCustomerIdException;
import uk.co.mruoc.model.Customer;
import uk.co.mruoc.exception.CustomerIdAlreadyUsedException;

import java.util.List;

public class MongoCustomerService implements CustomerService {

    private static final String COLLECTION_NAME = "customers";

    private final MongoOperations mongo;
    private final CustomerValidator validator;

    public MongoCustomerService(MongoOperations mongo) {
        this.mongo = mongo;
        this.validator = new CustomerValidator();
        createCollectionIfDoesNotExist();
    }

    @Override
    public List<Customer> getCustomers() {
        Query query = new SortByIdQuery();
        return mongo.find(query, Customer.class, COLLECTION_NAME);
    }

    @Override
    public List<Customer> getCustomers(int limit, int offset) {
        Query query = new PaginationQuery(limit, offset);
        return mongo.find(query, Customer.class, COLLECTION_NAME);
    }

    @Override
    public Customer getCustomer(String id) {
        if (alreadyExists(id)) {
            Query query = new SingleCustomerQuery(id);
            return mongo.findOne(query, Customer.class, COLLECTION_NAME);
        }
        throw new CustomerNotFoundException(id);
    }

    @Override
    public int getNumberOfCustomers() {
        return new Long(mongo.count(new Query(), COLLECTION_NAME)).intValue();
    }

    @Override
    public void create(Customer customer) {
        if (alreadyExists(customer.getId()))
            throw new CustomerIdAlreadyUsedException(customer.getId());

        validator.validate(customer);

        upsert(customer);
    }

    @Override
    public void update(Customer customer) {
        if (!alreadyExists(customer.getId()))
            throw new CustomerNotFoundException(customer.getId());

        validator.validate(customer);

        upsert(customer);
    }

    @Override
    public void delete(String id) {
        Query query = new SingleCustomerQuery(id);
        mongo.remove(query, Customer.class, COLLECTION_NAME);
    }

    private void createCollectionIfDoesNotExist() {
        if (!mongo.collectionExists(COLLECTION_NAME))
            mongo.createCollection(COLLECTION_NAME);
    }

    private void upsert(Customer customer) {
        Query query = new SingleCustomerQuery(customer);
        Update update = new CustomerUpdate(customer);
        mongo.upsert(query, update, Customer.class, COLLECTION_NAME);
    }

    private boolean alreadyExists(String id) {
        Query query = new SingleCustomerQuery(id);
        long count = mongo.count(query, COLLECTION_NAME);
        return count > 0;
    }

    private class SingleCustomerQuery extends Query {

        public SingleCustomerQuery(Customer customer) {
            this(customer.getId());
        }

        public SingleCustomerQuery(String id) {
            super(Criteria.where("_id").is(id));
        }

    }

    private class SortByIdQuery extends Query {

        public SortByIdQuery() {
            with(new Sort(Sort.Direction.ASC, "_id"));
        }

    }

    private class PaginationQuery extends SortByIdQuery {

        public PaginationQuery(int limit, int offset) {
            limit(limit);
            skip(offset);
        }

    }

    private class CustomerUpdate extends Update {

        public CustomerUpdate(Customer customer) {
            set("firstName", customer.getFirstName());
            set("surname", customer.getSurname());
            set("balance", customer.getBalance());
        }

    }

}
