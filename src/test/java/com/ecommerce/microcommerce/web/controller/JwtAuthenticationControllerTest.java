package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.JwtRequest;
import com.ecommerce.microcommerce.model.JwtResponse;
import com.ecommerce.microcommerce.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAuthenticationControllerTest {

    private String baseUrl = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    //@Ignore
    public void createAuthenticationToken() throws Exception{
        String url = baseUrl+port+"/authenticate";
        JwtRequest bodyAuthorized = new JwtRequest("kevin@example.fr", "password");
        JwtRequest bodyUnauthorized = new JwtRequest("unauthorized@example.fr", "password");

        ResponseEntity responseAuth = restTemplate.postForEntity(url, bodyAuthorized, String.class);
        assertThat(responseAuth.getStatusCode()).isEqualTo(HttpStatus.OK);

        //ResponseEntity responseUnauth = restTemplate.postForEntity(url, bodyUnauthorized, String.class);
        //assertThat(responseUnauth.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    }
}
