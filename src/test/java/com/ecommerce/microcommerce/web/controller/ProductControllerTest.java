package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.JwtRequest;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private String baseUrl = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void listeProduits() throws Exception {
        ResponseEntity requestOK = this.restTemplate.getForEntity(baseUrl + port + "/Produits", String.class);
        assertThat(requestOK.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void afficherUnProduit() throws Exception {
        ResponseEntity requestOK = this.restTemplate.getForEntity(baseUrl + port + "/Produits/1", String.class);
        assertThat(requestOK.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity requestNotFound = this.restTemplate.getForEntity(baseUrl + port + "/Produits/4", String.class);
        assertThat(requestNotFound.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void ajouterProduit() throws Exception {
        Product bodyRequestCreated = new Product(5, "Produit", 10, 10);
        ResponseEntity requestCreated = this.restTemplate.postForEntity(baseUrl + port + "/Produits", bodyRequestCreated, String.class);
        assertThat(requestCreated.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Product bodyRequestFree = new Product(5, "Produit", 0, 0);
        ResponseEntity requestFree = this.restTemplate.postForEntity(baseUrl + port + "/Produits", bodyRequestFree, String.class);
        assertThat(requestFree.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    public void supprimerProduit() throws Exception {
        ResponseEntity requestOK = this.restTemplate.exchange(baseUrl + port + "/Produits/1", HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertThat(requestOK.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity requestFailed = this.restTemplate.exchange(baseUrl + port + "/Produits/4", HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertThat(requestFailed.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateProduit() throws Exception {
        Product bodyRequestOk = new Product(1, "Produit", 10, 10);
        ResponseEntity requestOK = this.restTemplate.exchange(baseUrl + port + "/Produits/", HttpMethod.PUT, new HttpEntity<>(bodyRequestOk), String.class);
        assertThat(requestOK.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testeDeRequetes() throws Exception {
        ResponseEntity requestOK = this.restTemplate.getForEntity(baseUrl + port + "/test/produits/100", String.class);
        assertThat(requestOK.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity requestNotFound= this.restTemplate.getForEntity(baseUrl + port + "/test/produits/1000", String.class);
        assertThat(requestNotFound.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void calculerMargeProduits() throws Exception {
        JwtRequest jwtRequest= new JwtRequest("kevin@example.fr", "password");

        //unauth
        ResponseEntity requestUnAuth = this.restTemplate.getForEntity(baseUrl + port + "/AdminProduits", String.class);
        assertThat(requestUnAuth.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        //auth
        ResponseEntity reqAuth = this.restTemplate.postForEntity(baseUrl + port + "/authenticate", jwtRequest, String.class);
        //assertThat(reqAuth.getStatusCode()).isEqualTo(HttpStatus.OK);

        JsonObject jsonObject = new JsonParser().parse(reqAuth.getBody().toString()).getAsJsonObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+jsonObject.get("token").getAsString());
        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity requestAuth = this.restTemplate.exchange(baseUrl + port + "/AdminProduits", HttpMethod.GET, httpEntity, String.class);
        assertThat(requestAuth.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void trierProduitsParOrdreAlphabetique() throws Exception {
        ResponseEntity requestOK = this.restTemplate.getForEntity(baseUrl + port + "/ProduitsTries", String.class);
        assertThat(requestOK.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
