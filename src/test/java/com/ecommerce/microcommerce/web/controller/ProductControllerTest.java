package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitInternalErrorException;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    }

    @Test
    public void trierProduitsParOrdreAlphabetique() throws Exception {
        ResponseEntity requestOK = this.restTemplate.getForEntity(baseUrl + port + "/ProduitsTries", String.class);
        assertThat(requestOK.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
