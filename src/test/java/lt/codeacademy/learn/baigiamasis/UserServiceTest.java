package lt.codeacademy.learn.baigiamasis;

import jakarta.transaction.Transactional;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationToken;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationTokenRepository;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationTokenService;
import lt.codeacademy.learn.baigiamasis.user.User;
import lt.codeacademy.learn.baigiamasis.user.UserRepository;
import lt.codeacademy.learn.baigiamasis.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Test
    void testFindAllAndSaveUser() {
        List<User> userList = userService.findAll();
        Assertions.assertNotNull(userList);

        //Test for userService save
        User user = new User("Test", "Test", "Test");
        user = userService.save(user);

        List<User> userListWithTestUsers = userService.findAll();
        Assertions.assertNotNull(userListWithTestUsers);
        Assertions.assertEquals(userList.size() + 1, userListWithTestUsers.size());

        userService.deleteById(user.getId());
    }

    @Test
    void testLoadUserByUsername(){
        User user = new User("Test1", "Test1", "Test1");
        user = userRepository.save(user);

        UserDetails userDetails = userService.loadUserByUsername("Test1");
        Assertions.assertNotNull(userDetails);

        userService.deleteById(user.getId());
    }

    @Test
    void testDeleteUserById(){
        User user = new User("Test2", "Test2", "Test2");
        user = userRepository.save(user);
        List<User> userList = userService.findAll();

        userService.deleteById(user.getId());
        List<User> userListWhenDeletedUser = userService.findAll();
        Assertions.assertEquals(userList.size()-1, userListWhenDeletedUser.size());

    }

    @Test
    void testDeleteUserById_userNotFound() {
        Long id = 0L;

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.deleteById(id));
    }

    @Test
    void testDeleteUserById_cantDeleteAdmin() {
        Optional<User> adminUserOptional = userRepository.findByEmail("admin");
        Assertions.assertTrue(adminUserOptional.isPresent());

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.deleteById(adminUserOptional.get().getId()));
    }

    @Test
    void testUserFindById(){
        User user = new User("Test3", "Test3", "Test3");
        user = userRepository.save(user);
        Optional<User> userFromId = userService.findById(user.getId());

        Assertions.assertTrue(userFromId.isPresent());
        Assertions.assertEquals("Test3", userFromId.get().getEmail());

        userService.deleteById(userFromId.get().getId());

    }

    @Test
    @Transactional
    void testEnableUser(){
        User user = new User("Tes4", "Test4", "Test4");
        user = userService.save(user);
        Assertions.assertEquals(false, user.getEnabled());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.enableUser("non-existent-email");
        });

        userService.enableUser("Test4");

        Optional<User> enabledUser = userRepository.findById(user.getId());

        Assertions.assertTrue(enabledUser.isPresent());
        Assertions.assertEquals(true, enabledUser.get().getEnabled());

        userService.deleteById(enabledUser.get().getId());
    }

    @Test
    void testPasswordsMatch() {
        String oldPassword = "password123";
        String newPassword = "password456";

        // Test for successful match
        boolean passwordsMatch = userService.passwordsMatch(oldPassword, passwordEncoder.encode(oldPassword));
        Assertions.assertTrue(passwordsMatch);

        // Test for failed match
        passwordsMatch = userService.passwordsMatch(oldPassword, passwordEncoder.encode(newPassword));
        Assertions.assertFalse(passwordsMatch);
    }

    @Test
    void testPasswordEncoder() {
        String plainTextPassword = "password123";
        String encodedPassword = userService.passwordEncoder(plainTextPassword);

        Assertions.assertNotNull(encodedPassword);
        Assertions.assertNotEquals(plainTextPassword, encodedPassword);
    }

    @Test
    void testSignUpUser() {
        User user = new User("Test5", "Test5", "Test5", "1234");
        String token = userService.signUpUser(user);

        Assertions.assertThrows(IllegalStateException.class, () -> userService.signUpUser(user));

        Assertions.assertNotNull(token);

        Optional<User> savedUser = userRepository.findByEmail("Test5");
        Assertions.assertTrue(savedUser.isPresent());

        Optional<ConfirmationToken> savedToken = confirmationTokenRepository.findByToken(token);
        Assertions.assertNotNull(savedToken);

        userService.deleteById(savedUser.get().getId());
    }

}
