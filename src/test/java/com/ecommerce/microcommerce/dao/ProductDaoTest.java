package com.ecommerce.microcommerce.dao;


import com.ecommerce.microcommerce.model.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
public class ProductDaoTest {
    @Autowired
    private ProductDao productDao;

    @Test
    public void findAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "nom");
        List<Product> results = productDao.findAll(sort);
        assertThat(results).isNotNull();
    }
}
