package com.example.newsfeed_jwt.user.repository;

import com.example.newsfeed_jwt.domain.user.entity.User;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로_사용자를_조회할_수_있다() {
        //given
        String email = "asdf@asdf.com";
        LocalDate birthday = LocalDate.ofEpochDay(2002-2-12);
        User user = new User("name", email, "password", "image", birthday, "01098761234");
        userRepository.save(user);
        System.out.println("이거 통과함?");
        //when
        User foundUser = userRepository.findByEmail(email).orElse(null);

        //then
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }
}
