package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private UserController userController;
    private CartController cartController;
    private ItemController itemController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController,"itemRepository",itemRepository);
    }

    @Test
    public void getAllItemsTest(){
        Item item1 = new Item();
        item1.setId(1L);
        item1.setPrice(new BigDecimal("1.2"));
        item1.setDescription("Mac Book Pro");
        item1.setName("Apple Laptop");
        itemRepository.save(item1);
        Item item2 = new Item();
        item2.setId(2L);
        item2.setPrice(new BigDecimal("1.1"));
        item2.setDescription("HP Spectre");
        item2.setName("Windows Laptop");
        itemRepository.save(item2);
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void getItemById(){
        Item item1 = new Item();
        item1.setId(1L);
        item1.setPrice(new BigDecimal("1.2"));
        item1.setDescription("Mac Book Pro");
        item1.setName("Apple Laptop");
        itemRepository.save(item1);
        Item item2 = new Item();
        item2.setId(2L);
        item2.setPrice(new BigDecimal("1.1"));
        item2.setDescription("HP Spectre");
        item2.setName("Windows Laptop");
        itemRepository.save(item2);
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        final ResponseEntity<Item> response = itemController.getItemById(1L);
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void getItemByNameNotFoundTest(){
        Item item1 = new Item();
        item1.setId(1L);
        item1.setPrice(new BigDecimal("1.2"));
        item1.setDescription("Mac Book Pro");
        item1.setName("Apple Laptop");
        itemRepository.save(item1);
        Item item2 = new Item();
        item2.setId(2L);
        item2.setPrice(new BigDecimal("1.1"));
        item2.setDescription("HP Spectre");
        item2.setName("Windows Laptop");
        itemRepository.save(item2);
        final ResponseEntity<List<Item>> response = itemController.getItemsByName("Dell");
        assertEquals(404,response.getStatusCodeValue());
    }
}
