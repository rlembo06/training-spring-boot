package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.User;
import com.ecommerce.microcommerce.model.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findByUsername(){
        UserDTO userDto = new UserDTO();
        userDto.setUsername("kevin@example.fr");

        UserDTO userDtoUnauth = new UserDTO();
        userDtoUnauth.setUsername("not_a_valid_user@example.fr");

        //user trouvé
        User userFound = userDao.findByUsername(userDto.getUsername());
        assertThat(userFound.getUsername()).isEqualTo(userDto.getUsername());

        //user pas trouvé
        User userNotFound = userDao.findByUsername(userDtoUnauth.getUsername());
        assertThat(userNotFound).isEqualTo(null);
    }
}
