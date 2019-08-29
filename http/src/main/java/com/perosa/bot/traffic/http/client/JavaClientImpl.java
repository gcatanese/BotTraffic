package com.perosa.bot.traffic.http.client;

import com.perosa.bot.traffic.http.client.wrap.Get;
import com.perosa.bot.traffic.http.client.wrap.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JavaClientImpl implements RoutingClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaClientImpl.class);

    private static final String JSON_CONTENT_TYPE = "application/json";

    public RoutingClientResponse get(Get input) throws Exception {
        return call(input.getUrl() + input.getPath(), "GET", input.getHeaders());
    }

    public RoutingClientResponse post(Post input) throws Exception {
        return call(input.getUrl() + input.getPath(), "POST", input.getHeaders(), input.getBody());
    }

    RoutingClientResponse call(String endpoint, String method, Map<String, String> headers) throws Exception {
        return call(endpoint, method, headers, null);
    }

    RoutingClientResponse call(String endpoint, String method, Map<String, String> headers, String payload) throws Exception {

        RoutingClientResponse routingClientResponse = new RoutingClientResponse();

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(endpoint);

            if (url.getProtocol().equalsIgnoreCase("https")) {
                connection = (HttpsURLConnection) url.openConnection();
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }
            //  Set connection properties
            connection.setRequestMethod(method);
            connection.setReadTimeout(3 * 1000);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            setHeaders(connection, headers);

            if (payload != null) {
                OutputStream os = connection.getOutputStream();

                os.write(payload.getBytes(StandardCharsets.UTF_8));

                os.flush();
                os.close();
            }

            int responseCode = connection.getResponseCode();

            if (responseCode != 0) {

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder stringBuilder = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                routingClientResponse.setResponseCode(connection.getResponseCode());
                routingClientResponse.setBody(stringBuilder.toString());
                routingClientResponse.setHeaders(connection.getHeaderFields());

            } else {
                LOGGER.error("responseCode:" + responseCode + " responseMessage:" + connection.getResponseMessage());
                throw new RuntimeException("responseCode:" + responseCode + " message:" + connection.getResponseMessage());
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return routingClientResponse;
    }

    void setHeaders(HttpURLConnection connection, Map<String, String> headers) {
        if (headers.get("Content-Type") != null) {
            connection.setRequestProperty("Content-Type", headers.get("Content-Type"));
        }
        if (headers.get("Accept") != null) {
            connection.setRequestProperty("Accept", headers.get("Accept"));
        }
        if (headers.get("Host") != null) {
            connection.setRequestProperty("Host", headers.get("Host"));
        }
        if (headers.get("Accept-Encoding") != null) {
            connection.setRequestProperty("Accept-Encoding", headers.get("Accept-Encoding"));
        }
        if (headers.get("User-Agent") != null) {
            connection.setRequestProperty("User-Agent", headers.get("User-Agent"));
        }
        if (headers.get("Authorization") != null) {
            connection.setRequestProperty("Authorization", headers.get("Authorization"));
        }
    }


}
