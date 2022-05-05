package sprint2.socialmeli.unit.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sprint2.socialmeli.model.User;
import sprint2.socialmeli.repository.ISocialMeliRepository;
import sprint2.socialmeli.repository.SocialMeliRepository;

public class SocialMeliRepositoryTest {

    ISocialMeliRepository socialMeliRepository;

    @BeforeEach
    void setUp() {
        socialMeliRepository = new SocialMeliRepository();
    }

    @Test
    @DisplayName("Comprobar que encuentra un usuario por Id")
    void testSearchUserById() {
        Integer id = 1;
        User expected = new User(id, "Lorena Maciel");
        User result = socialMeliRepository.findUserById(id);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Comprobar que no encuentra un usuario por Id y devuelve null")
    void testSearchUserByIdReturnsNullWhenUserNotFound() {
        Integer id = -1;
        User result = socialMeliRepository.findUserById(id);
        Assertions.assertEquals(null, result);
    }

    @Test
    @DisplayName("Comprobar que un usuario existe")
    void testUserExists() {
        Integer id = 1;
        Assertions.assertTrue(socialMeliRepository.existUser(id));
    }

    @Test
    @DisplayName("Comprobar que un usuario no existe")
    void testUserDoesNotExist() {
        Integer id = -1;
        Assertions.assertFalse(socialMeliRepository.existUser(id));
    }
}
