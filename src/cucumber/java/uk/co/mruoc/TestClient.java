package uk.co.mruoc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import uk.co.mruoc.dto.CustomerDto;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class TestClient {

    private static final String CUSTOMERS_URL = "http://localhost:8080/web-template/ws/v1/customers";

    private final Gson gson = new Gson();

    public void createCustomer(CustomerDto customer) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = createPost(customer);
            System.out.println("posting customer " + customer.getFullName());
            try (CloseableHttpResponse response = client.execute(post)) {
                System.out.println("response " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<CustomerDto> getAllCustomers() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(CUSTOMERS_URL);
            try (CloseableHttpResponse response = client.execute(get)) {
                return getCustomers(response);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private HttpPost createPost(CustomerDto customer) {
        HttpPost post = new HttpPost(CUSTOMERS_URL);
        post.setEntity(toEntity(customer));
        post.setHeader("Content-type", "application/json");
        return post;
    }

    private HttpEntity toEntity(CustomerDto customer) {
        try {
            System.out.println("building entity with string " + gson.toJson(customer));
            return new StringEntity(gson.toJson(customer));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CustomerDto> getCustomers(CloseableHttpResponse response) {
        String content = readContent(response);
        return gson.fromJson(content, new TypeToken<List<CustomerDto>>(){}.getType());
    }


    private String readContent(CloseableHttpResponse response) {
        try {
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
                return buffer.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
