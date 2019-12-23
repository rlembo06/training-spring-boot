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
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private Gson gson = new Gson();;

    private String baseUrl = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private ProductController productController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void listeProduits() throws Exception {
        MappingJacksonValue list = productController.listeProduits();
        String expected = gson.toJson(list.getValue());
        String request = this.restTemplate.getForObject(baseUrl + port + "/Produits", String.class);

        assertThat(request).contains(expected);
    }

    @Test
    public void afficherUnProduit() throws Exception {
        Product item = productController.afficherUnProduit(1);
        String expected = gson.toJson(item);
        String request = this.restTemplate.getForObject(baseUrl + port + "/Produits/1", String.class);

        assertThat(request).contains(expected);
    }

    @Test
    public void ajouterProduit() throws Exception {
        Product bodyRequest = new Product(5, "Produit", 10, 10);
        String request = this.restTemplate.postForObject(baseUrl + port + "/Produits/", bodyRequest, String.class);

        assertThat(request).isNullOrEmpty();
    }

    @Test
    public void trierProduitsParOrdreAlphabetique() throws Exception {
        List<Product> list = productController.trierProduitsParOrdreAlphabetique();
        String expected = gson.toJson(list);

        String request = this.restTemplate.getForObject(baseUrl + port + "/ProduitsTries", String.class);
        assertThat(request).contains(expected);
        /*assertThatThrownBy(() -> {
            productController.trierProduitsParOrdreAlphabetique();
        }).isInstanceOf(ProduitInternalErrorException.class);*/
    }

    /*@Test
    public void trierProduitsParOrdreAlphabetique() {
        assertThatThrownBy(() -> {
                productController.trierProduitsParOrdreAlphabetique();
        }).isInstanceOf(ProduitInternalErrorException.class);
    }*/

}
