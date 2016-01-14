package uk.co.mruoc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import uk.co.mruoc.dto.CustomerDto;
import uk.co.mruoc.dto.ErrorDto;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerResponse {

    private final Gson gson = new Gson();
    private final int statusCode;
    private final String statusMessage;
    private final String content;

    public CustomerResponse(CloseableHttpResponse response) {
        this.statusCode = getStatusCode(response);
        this.statusMessage = getStatusMessage(response);
        this.content = getContent(response);

        System.out.println("got status code " + statusCode);
        System.out.println("got content " + content);
    }

    public CustomerDto getCustomer() {
        return gson.fromJson(content, new TypeToken<CustomerDto>(){}.getType());
    }

    public List<CustomerDto> getCustomers() {
        return gson.fromJson(content, new TypeToken<List<CustomerDto>>(){}.getType());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ErrorDto getError() {
        return gson.fromJson(content, new TypeToken<ErrorDto>(){}.getType());
    }

    private int getStatusCode(CloseableHttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    private String getStatusMessage(CloseableHttpResponse response) {
        return response.getStatusLine().getReasonPhrase();
    }

    private String getContent(CloseableHttpResponse response) {
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
