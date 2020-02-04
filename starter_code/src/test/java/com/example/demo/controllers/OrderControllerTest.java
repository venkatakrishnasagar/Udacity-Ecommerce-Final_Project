package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);


    @Before
    public void setUp(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController,"orderRepository",orderRepository);
        TestUtils.injectObjects(orderController,"userRepository",userRepository);
    }

    @Test
    public void orderControllerTest(){
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
        List<Item> items = new ArrayList<>();
        items.add(item);
        UserOrder userOrder = new UserOrder();
        userOrder.setItems(items);
        userOrder.setTotal(new BigDecimal("2"));
        userOrder.setUser(user);
        userOrder.setId(1L);
        System.out.println("user get cart "+user.getCart());
        user.getCart().setUser(user);
        user.getCart().setTotal(new BigDecimal("1.2"));
        user.getCart().setId(1L);
        user.getCart().setItems(items);
        final ResponseEntity<UserOrder> response = orderController.submit("sagar");
        assertEquals(200,response.getStatusCodeValue());
    }

}
