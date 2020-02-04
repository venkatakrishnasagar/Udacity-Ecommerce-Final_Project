package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private UserController userController;
    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController,"userRepository",userRepository);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepository);
        TestUtils.injectObjects(cartController,"cartRepository",cartRepository);
    }
    @Test
    public void addItemToCartSuccess(){

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1);
        request.setUsername("sagar");
        request.setQuantity(1);
        User user = new User();
        user.setUsername("sagar");
        user.setId(1);
        Cart userCart = new Cart();

       Item item = new Item();
       item.setId(1L);
       item.setDescription("mac book pro");
       item.setName("laptop");
       item.setPrice(new BigDecimal("1.25"));
        userCart.setId(1L);
        userCart.setUser(user);
        userCart.setTotal(new BigDecimal("1.25"));
        user.setCart(userCart);
        when(userRepository.findByUsername("sagar")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        final ResponseEntity<Cart> response = cartController.addTocart(request);
        System.out.println("Response "+response);
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void removeCartTest(){

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1);
        request.setUsername("sagar");
        request.setQuantity(1);
        User user = new User();
        user.setUsername("sagar");
        user.setId(1);
        Cart userCart = new Cart();
        Item item = new Item();
        item.setId(1L);
        item.setDescription("mac book pro");
        item.setName("laptop");
        item.setPrice(new BigDecimal("1.25"));
        userCart.setId(1L);
        userCart.setUser(user);
        userCart.setTotal(new BigDecimal("1.25"));
        user.setCart(userCart);
        when(userRepository.findByUsername("sagar")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        final ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertEquals(200,response.getStatusCodeValue());
    }
}
