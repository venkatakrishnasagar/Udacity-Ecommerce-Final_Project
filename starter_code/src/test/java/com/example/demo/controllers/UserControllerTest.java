package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObjects(userController,"userRepository",userRepository);
        TestUtils.injectObjects(userController,"cartRepository",cartRepository);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder",encoder);
    }

    @Test
    public void createUserSuccess() throws Exception{
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0,u.getId());
        assertEquals("test",u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());
    }

    @Test
    public void createUserFailure() throws Exception{
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPasswords");
        final ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void getUserBydIdSuccess() throws Exception{
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        final ResponseEntity<User> createUserResponse = userController.createUser(r);
        System.out.println(createUserResponse);
        User u = createUserResponse.getBody();
        System.out.println(u.getUsername());
        when(userRepository.findByUsername("test")).thenReturn(u);
        final ResponseEntity<User> getUserResponse = userController.findByUserName("test");
        System.out.println("response "+getUserResponse);
        assertEquals(200,getUserResponse.getStatusCodeValue());
        assertEquals("test",getUserResponse.getBody().getUsername());
    }

    @Test
    public void getUserByIdSuccess(){
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        final ResponseEntity<User> createUserResponse = userController.createUser(r);
        System.out.println(createUserResponse);
        User u = createUserResponse.getBody();
        System.out.println(u.getUsername());
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(u));
        final ResponseEntity<User> getUserResponse = userController.findById(1L);
        System.out.println("response "+getUserResponse);
        assertEquals(200,getUserResponse.getStatusCodeValue());
        assertEquals(0,getUserResponse.getBody().getId());
    }
}
