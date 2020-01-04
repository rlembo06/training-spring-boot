package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductDaoTest {
    //@Autowired
    //private TestEntityManager testEntityManager;

    @Autowired
    private ProductDao productDao;

    /*@Before
    public void setUp(){
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "product1", 10, 20));
        testEntityManager.persist(products);
    }*/

    /*@Before
    public void setUp(){
        Product product = new Product(1, "product1", 10, 20);
        testEntityManager.persist(product);
    }*/

    @Test
    public void findAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "nom");
        List<Product> results = productDao.findAll(sort);
        assertThat(results).hasSize(3);
    }

    @Test
    public void findById() {
        Product result = productDao.findById(1);
        assertThat(result).isNotNull();
    }

    @Test
    public void findByPrixGreaterThan() {
        List<Product> results1 = productDao.findByPrixGreaterThan(499);
        assertThat(results1).hasSize(2);
        List<Product> results2 = productDao.findByPrixGreaterThan(1000);
        assertThat(results2).hasSize(0);
    }

    @Test
    public void findByNomLike() {
        List<Product> results1 = productDao.findByNomLike("Ordinateur portable");
        assertThat(results1).hasSize(1);
        List<Product> results2 = productDao.findByNomLike("No result");
        assertThat(results2).hasSize(0);
    }

    @Test
    public void chercherUnProduitCher() {
        List<Product> results1 = productDao.chercherUnProduitCher(499);
        assertThat(results1).hasSize(2);
        List<Product> results2 = productDao.chercherUnProduitCher(100);
        assertThat(results2).hasSize(3);
    }
}
