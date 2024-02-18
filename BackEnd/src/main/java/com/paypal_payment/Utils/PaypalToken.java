package com.paypal_payment.Utils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Base64;

@Component
public class PaypalToken {
    @Value("${clientid}")
    private String CLIENT_ID;
    @Value("${clientsecret}")
    private String CLIENT_SECRET;
    @Value("${ACCESS_TOKEN_URL}")
    private String ACCESS_TOKEN_URL;
    public String getAccessToken() throws IOException {
        String authString = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());
        URL url = new URL(ACCESS_TOKEN_URL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String postData = "grant_type=client_credentials";
        conn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(postData);
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder responseContent = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }
        reader.close();
        JSONObject response = new JSONObject(responseContent.toString());
        String accessToken = response.getString("access_token");
        return accessToken;
    }
}
