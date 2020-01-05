package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    @Query("FROM User u where u.username=:username")
    User findByUsername(@Param("username") String username);

}