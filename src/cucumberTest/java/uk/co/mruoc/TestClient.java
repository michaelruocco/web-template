package uk.co.mruoc;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.mruoc.controller.dto.CustomerDto;

import java.io.*;

public class TestClient {

    private static final Logger LOG = LoggerFactory.getLogger(TestClient.class);

    private static final String CUSTOMERS_URL = "http://localhost:8080/ws/v1/customers";
    private static final String PAGED_CUSTOMERS_URL = CUSTOMERS_URL + "?limit=%d&offset=%d";
    private static final String CUSTOMER_URL = CUSTOMERS_URL + "/%s";

    private final Gson gson = new Gson();

    public CustomerResponse createCustomer(CustomerDto customer) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = createPost(customer);
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

    public CustomerResponse getCustomer(String id) {
        String url = String.format(CUSTOMER_URL, id);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = createGet(url);
            try (CloseableHttpResponse response = client.execute(get)) {
                return new CustomerResponse(response);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public CustomerResponse updateCustomer(CustomerDto customer) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut put = createPut(customer);
            try (CloseableHttpResponse response = client.execute(put)) {
                return new CustomerResponse(response);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public CustomerResponse deleteCustomer(String id) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpDelete delete = createDelete(id);
            try (CloseableHttpResponse response = client.execute(delete)) {
                return new CustomerResponse(response);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private HttpPost createPost(CustomerDto customer) {
        logInfo("creating POST request for " + CUSTOMERS_URL);
        HttpPost post = new HttpPost(CUSTOMERS_URL);
        post.setEntity(toEntity(customer));
        post.setHeader("Content-type", "application/json");
        return post;
    }

    private HttpPut createPut(CustomerDto customer) {
        logInfo("creating PUT request for " + CUSTOMERS_URL);
        HttpPut put = new HttpPut(CUSTOMERS_URL);
        put.setEntity(toEntity(customer));
        put.setHeader("Content-type", "application/json");
        return put;
    }

    private HttpGet createGet(String url) {
        logInfo("creating GET request for " + url);
        return new HttpGet(url);
    }

    private HttpDelete createDelete(String id) {
        String url = String.format(CUSTOMER_URL, id);
        logInfo("creating DELETE request for " + url);
        return new HttpDelete(url);
    }

    private HttpEntity toEntity(CustomerDto customer) {
        try {
            String json = gson.toJson(customer);
            logInfo("building entity with string " + json);
            return new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private int calculateOffset(int pageSize, int pageNumber) {
        return ((pageNumber - 1) * pageSize);
    }

    private void logInfo(String message) {
        LOG.info(message);
    }

}
