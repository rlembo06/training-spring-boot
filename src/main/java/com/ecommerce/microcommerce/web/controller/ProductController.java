package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api( description="API pour es opérations CRUD sur les produits.", tags = "products")

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    //Récupérer la liste des produits
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();
        if(IterableUtils.size(produits) == 0) throw new ProduitIntrouvableException("Aucun produit.");

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);
        return produitsFiltres;
    }

    //Récupérer un produit par son Id
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        Product produit = productDao.findById(id);
        if(produit==null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        return produit;
    }

    //ajouter un produit
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {
        if(product.getPrix() <= 0){
            throw new ProduitGratuitException("Impossible d'ajouter le produit car il est gratuit");
        }
        Product productAdded = productDao.save(product);
        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {
        productDao.delete(id);
    }

    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product) {
        productDao.save(product);
    }

    //Pour les tests
    @GetMapping(value = "test/produits/{prix}")
    public List<Product> testeDeRequetes(@PathVariable int prix) {
        List<Product> produits = productDao.chercherUnProduitCher(prix);
        if(produits.size() == 0) throw new ProduitIntrouvableException("Aucun produit cher au dessus de "+ prix +" euros.");
        return produits;
    }

    @ApiOperation(value = "Affiche la marge pour chaque produit")
    @GetMapping(value = "/AdminProduits")
    public Map<String, Integer> calculerMargeProduits(){
        List<Product> products = productDao.findAll();
        if(products.size() == 0) throw new ProduitIntrouvableException("Aucun produit.");
        Map<String, Integer> result = new HashMap<>();
        for(Product product: products) {
            int marge = product.getPrix() - product.getPrixAchat();
            result.put(product.toString(), marge);
        }
        return result;
    }

    //Récupérer la liste des produits alphabetiquement
    @ApiOperation(value = "Récupérer la liste des produits alphabetiquement")
    @GetMapping(value = "/ProduitsTries")
    public List<Product> trierProduitsParOrdreAlphabetique() {
        Sort sort = new Sort(Sort.Direction.ASC, "nom");
        List<Product> produits = productDao.findAll(sort);
        if(produits.size() == 0) throw new ProduitIntrouvableException("Aucun produit.");
        return produits;
    }

}
