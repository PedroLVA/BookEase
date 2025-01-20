package com.bookease.bookease.repositories;
import com.bookease.bookease.domain.Organizer;
import com.bookease.bookease.domain.Role;
import com.bookease.bookease.domain.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {


    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup(){
        user = new Organizer();
        user.setName("user");
        user.setEmail("user.ig@example.com");
        user.setPassword("Rockasa");
        user.setPhoneNumber("+1234567890");
        user.setDateOfBirth(LocalDateTime.now());
        user.setRole(Role.USER);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    @DisplayName("Should return the correct user by email")
    void findByEmailCase1() {
        User foundUser = (User) userRepository.findByEmail("user.ig@example.com");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("user.ig@example.com"); // Since UserDetails uses getUsername()
    }


    //maybe it should throw an exception
    @Test
    @DisplayName("Should return empty because email doesnt exist")
    void findByEmailCase2() {
        User foundUser = (User) userRepository.findByEmail("notexist.ig@example.com");

        assertThat(foundUser).isNull();

    }
}