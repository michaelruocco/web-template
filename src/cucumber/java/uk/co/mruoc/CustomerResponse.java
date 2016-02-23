package uk.co.mruoc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import uk.co.mruoc.controller.CustomerDto;
import uk.co.mruoc.controller.ErrorDto;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerResponse {

    private static final Logger LOG = Logger.getLogger(CustomerResponse.class);

    private final Gson gson = new Gson();
    private final int statusCode;
    private final Header[] headers;
    private final String statusMessage;
    private final String content;

    public CustomerResponse(CloseableHttpResponse response) {
        this.statusCode = getStatusCode(response);
        this.headers = response.getAllHeaders();
        this.statusMessage = getStatusMessage(response);
        this.content = getContent(response);
        logInfo("got status code " + statusCode);
        logInfo("got content " + content);
        logInfo("status message " + statusMessage);
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

    public String getHeader(String name) {
        for(Header header : headers) {
            if (header.getName().equals(name)) {
                return header.getValue();
            }
        }
        return "";
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
            if (entity == null) {
                return "";
            }
            InputStream stream = entity.getContent();
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
                return buffer.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void logInfo(String message) {
        LOG.info(message);
    }

}
