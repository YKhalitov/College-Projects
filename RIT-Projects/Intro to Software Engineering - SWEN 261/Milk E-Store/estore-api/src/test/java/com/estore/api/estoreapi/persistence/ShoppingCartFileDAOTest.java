package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.Milk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Persistence-tier")
public class ShoppingCartFileDAOTest {
    ShoppingCartDAO shoppingCartFileDAO;
    ShoppingCart[] testCarts;
    ObjectMapper mockObjectMapper;
   
    /**
     * Create and inject a Mock Object Mapper to isolate the tests
     * from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupShoppingCartFileDAO() throws IOException
    {
        mockObjectMapper = mock(ObjectMapper.class);

        testCarts = new ShoppingCart[2];
        // Setup
        ArrayList<Milk> listOfMilks1 = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks1.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks1.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));

        ArrayList<Integer> listOfQuantities1 = new ArrayList<Integer>();
        listOfQuantities1.add(2);
        listOfQuantities1.add(3);

        ArrayList<Milk> listOfMilks2 = new ArrayList<Milk>();
        listOfMilks2.add(new Milk(2, "cowsss", "bananazas", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks2.add(new Milk(23, "goat", "peaches", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));

        ArrayList<Integer> listOfQuantities2 = new ArrayList<Integer>();
        listOfQuantities2.add(3);
        listOfQuantities2.add(5);

        ShoppingCart shoppingCart1 = new ShoppingCart("Testing1", listOfMilks1, listOfQuantities1);
        ShoppingCart shoppingCart2 = new ShoppingCart("Testing2", listOfMilks2, listOfQuantities2);

        testCarts[0] = shoppingCart1;
        testCarts[1] = shoppingCart2;



        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper.readValue(new File("random.txt"), ShoppingCart[].class))
            .thenReturn(testCarts);
        shoppingCartFileDAO = new ShoppingCartFileDAO("random.txt", mockObjectMapper);
    }

    @Test
    public void testGetShoppingCarts() throws IOException 
    {
        ShoppingCart[] carts = shoppingCartFileDAO.getShoppingCarts();
        // Analyze
        assertEquals(carts.length, testCarts.length);
        for (int i = 0; i < testCarts.length; i++)
        {
            assertEquals(carts[i], testCarts[i]);
        }
    }

    @Test
    public void testGetShoppingCart() throws IOException{
        // Invoke
        ShoppingCart cart = shoppingCartFileDAO.getShoppingCart("Testing1");

        // Analyze
        assertEquals(cart,testCarts[0]);
    }

    @Test
    public void testGetShoppingCartNonExistant() throws IOException{
        // Invoke
        ShoppingCart cart = shoppingCartFileDAO.getShoppingCart("not there");

        // Analyze
        assertEquals(null, cart);
    }

    @Test
    public void testAddMilkExisting() throws IOException{
        double[] rating = {4.0};
        Milk milk = new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // Invoke
        boolean result = assertDoesNotThrow(() -> shoppingCartFileDAO.addMilk(milk, "Testing1"),
                            "Unexpected exception thrown");

        // Analyze
        assertEquals(true, result);
        // We check the internal tree map size against the length
        // of the test milks array - 1 (because of the delete)
        // Because milks attribute of MilkFileDAO is package private
        // we can access it directly

        assertEquals(3, shoppingCartFileDAO.getShoppingCart("Testing1").getMilksInCartQuantity().get(0));
    }

    @Test
    public void testAddMilkNonExisting() throws IOException{
        double[] rating = {4.0};
        Milk milk = new Milk(99, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // Invoke
        boolean result = assertDoesNotThrow(() -> shoppingCartFileDAO.addMilk(milk, "Testing1"),
                            "Unexpected exception thrown");

        // Analyze
        assertEquals(true, result);
        // We check the internal tree map size against the length
        // of the test milks array - 1 (because of the delete)
        // Because milks attribute of MilkFileDAO is package private
        // we can access it directly

        assertEquals(1, shoppingCartFileDAO.getShoppingCart("Testing1").getMilksInCartQuantity().get(2));
        assertEquals(milk, shoppingCartFileDAO.getShoppingCart("Testing1").getMilksInCart().get(2));
    }

    @Test
    public void testDecrementMilkExisting() throws IOException{
        double[] rating = {4.0};
        Milk milk = new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // Invoke
        boolean result = assertDoesNotThrow(() -> shoppingCartFileDAO.decrementMilk(milk.getId(), "Testing1"),
                            "Unexpected exception thrown");

        // Analyze
        assertEquals(true, result);
        // We check the internal tree map size against the length
        // of the test milks array - 1 (because of the delete)
        // Because milks attribute of MilkFileDAO is package private
        // we can access it directly

        assertEquals(1, shoppingCartFileDAO.getShoppingCart("Testing1").getMilksInCartQuantity().get(0));
    }

    @Test
    public void testDecrementMilkNonExisting() throws IOException{
        double[] rating = {4.0};
        Milk milk = new Milk(99, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // Invoke
        boolean result = assertDoesNotThrow(() -> shoppingCartFileDAO.decrementMilk(99, "Testing1"),
                            "Unexpected exception thrown");

        // Analyze
        assertEquals(true, result);
        // We check the internal tree map size against the length
        // of the test milks array - 1 (because of the delete)
        // Because milks attribute of MilkFileDAO is package private
        // we can access it directly

        assertEquals(shoppingCartFileDAO.getShoppingCart("Testing1"), testCarts[0]);
        assertEquals(shoppingCartFileDAO.getShoppingCart("Testing2"), testCarts[1]);
    }

    // @Test
    // public void testDecrementMilk() {
    //     // Invoke
    //     boolean result = assertDoesNotThrow(() -> milkFileDAO.deleteMilk(10),
    //                         "Unexpected exception thrown");

    //     // Analyze
    //     assertEquals(result, true);
    //     // We check the internal tree map size against the length
    //     // of the test milks array - 1 (because of the delete)
    //     // Because milks attribute of MilkFileDAO is package private
    //     // we can access it directly
    //     assertEquals(milkFileDAO.milks.size(),testMilks.length-1);
    // }
}
