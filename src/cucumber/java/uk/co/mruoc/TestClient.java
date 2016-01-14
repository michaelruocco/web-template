package uk.co.mruoc;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import uk.co.mruoc.dto.CustomerDto;

import java.io.*;

public class TestClient {

    private static final String CUSTOMERS_URL = "http://localhost:8080/web-template/ws/v1/customers";
    private static final String PAGED_CUSTOMERS_URL = CUSTOMERS_URL + "?limit=%d&offset=%d";
    private static final String GET_CUSTOMER_URL = CUSTOMERS_URL + "/%s";

    private final Gson gson = new Gson();

    public CustomerResponse createCustomer(CustomerDto customer) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = createPost(customer);
            System.out.println("posting customer " + customer.getFullName());
            try (CloseableHttpResponse response = client.execute(post)) {
                return new CustomerResponse(response);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public CustomerResponse getCustomers() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = createGet(CUSTOMERS_URL);
            try (CloseableHttpResponse response = client.execute(get)) {
                return new CustomerResponse(response);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public CustomerResponse getCustomers(int pageSize, int pageNumber) {
        int offset = calculateOffset(pageSize, pageNumber);
        String url = String.format(PAGED_CUSTOMERS_URL, pageSize, offset);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = createGet(url);
            try (CloseableHttpResponse response = client.execute(get)) {
                return new CustomerResponse(response);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public CustomerResponse getCustomer(String accountNumber) {
        String url = String.format(GET_CUSTOMER_URL, accountNumber);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = createGet(url);
            try (CloseableHttpResponse response = client.execute(get)) {
                return new CustomerResponse(response);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private HttpPost createPost(CustomerDto customer) {
        System.out.println("creating POST request for " + CUSTOMERS_URL);
        HttpPost post = new HttpPost(CUSTOMERS_URL);
        post.setEntity(toEntity(customer));
        post.setHeader("Content-type", "application/json");
        return post;
    }

    private HttpEntity toEntity(CustomerDto customer) {
        try {
            System.out.println("customer balance " + customer.getBalance());
            System.out.println("building entity with string " + gson.toJson(customer).replaceAll(".0", ""));
            return new StringEntity(gson.toJson(customer));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private int calculateOffset(int pageSize, int pageNumber) {
        return ((pageNumber - 1) * pageSize);
    }

    private HttpGet createGet(String url) {
        System.out.println("creating GET request for " + url);
        return new HttpGet(url);
    }

}
