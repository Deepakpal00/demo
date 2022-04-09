package com.example.demo.service;

import com.example.demo.expections.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Service
public class UserService {
    private final static String baseUrl = "https://reqres.in/api/users/";

    public ResponseEntity<String> getUserEmailIdById(String id) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Object> rs = restTemplate.getForEntity(baseUrl + id, Object.class);
            if (rs.getStatusCode() == HttpStatus.OK) {
                LinkedHashMap body = (LinkedHashMap) rs.getBody();
                if (body != null) {
                    LinkedHashMap userInfo = (LinkedHashMap) body.get("data");
                    String email = (String) userInfo.get("email");
                    return new ResponseEntity<>(email, HttpStatus.OK);
                } else {
                    throw new UserNotFoundException("User Not Found For id " + id + ".");
                }
            }
            throw new RuntimeException("EXTERNAL API CALL ERROR");
        } catch (RestClientException rc) {
            throw new UserNotFoundException("User Not Found For id " + id + ".");
        }
    }
}
