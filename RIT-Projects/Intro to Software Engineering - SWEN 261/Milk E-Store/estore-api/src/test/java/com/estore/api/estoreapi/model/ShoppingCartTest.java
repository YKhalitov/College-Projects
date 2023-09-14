package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class ShoppingCartTest {
    @Test
    public void testSetName()
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        String expectedUsername = "Jeremy";
        // Invoke
        cart.setUsername(expectedUsername);
        // Analyze
        assertEquals(expectedUsername, cart.getUsername());
    }

    @Test
    public void testGetMilksInCart()
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        // Invoke
        List<Milk> actual = cart.getMilksInCart();
        // Analyze
        assertEquals(actual, listOfMilks);
    }

    @Test
    public void testGetMilksInCartParam()
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        // Invoke
        Milk actual = cart.getMilksInCart(0);
        // Analyze
        assertEquals(listOfMilks.get(0), actual);
    }

    @Test
    public void testGetMilksInCartQuantity()
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        // Invoke
        List<Integer> actual = cart.getMilksInCartQuantity();
        // Analyze
        assertEquals(listOfMilksQuantity, actual);
    }

    @Test
    public void testGetMilksInCartQuantityParam()
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        // Invoke
        Integer actual = cart.getMilksInCartQuantity(0);
        // Analyze
        assertEquals(listOfMilksQuantity.get(0), actual);
    }

    @Test
    public void testSetMilksInCart()
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        ArrayList<Milk> secondListOfMilks = new ArrayList<Milk>();
        secondListOfMilks.add(new Milk(29, "almond", "strawberry", 4.7, 8, 5.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        secondListOfMilks.add(new Milk(30, "soy", "walnut", 3.2, 18, 3.89, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        //Invoke
        cart.setMilksInCart(secondListOfMilks);
        // Analyze
        assertEquals(secondListOfMilks, cart.getMilksInCart());
    }

    @Test
    public void testSetSingleMilkInCart()
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        Milk milk = new Milk(28, "hemp", "cinnamon", 4.3, 27, 5.12, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // Invoke
        cart.setMilksInCart(0, milk);
        // Analyze
        assertEquals(milk, cart.getMilksInCart(0));
    }

    @Test
    public void testSetMilksInCartQuantity()
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        ArrayList<Integer> listOfMilksQuantity2 = new ArrayList<Integer>();
        listOfMilksQuantity2.add(9);
        listOfMilksQuantity2.add(6);
        // Invoke
        cart.setMilksInCartQuantity(listOfMilksQuantity2);
        // Analyze
        assertEquals(listOfMilksQuantity2, cart.getMilksInCartQuantity());
    }

    @Test
    public void testSetOneMilkInCartQuantity()
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        Integer newQuantity = 14;
        // Invoke
        cart.setMilksInCartQuantity(0, newQuantity);
        // Analyze
        assertEquals(newQuantity, cart.getMilksInCartQuantity(0));
    }

    @Test
    public void testIncrementMilk() throws IOException
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        Milk milk = new Milk(20, "coconut", "peach", 3.9, 17, 4.57, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // Invoke
        boolean result = cart.incrementItem(listOfMilks.get(0));
        boolean result2 = cart.incrementItem(milk);
        // Analyze
        assertEquals(true, result);
        assertEquals(true, result2);
    }

    @Test
    public void testDecrementMilk() throws IOException
    {
        // Setup
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 1, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(10);
        listOfMilksQuantity.add(1);
        ShoppingCart cart = new ShoppingCart("Yaro", listOfMilks, listOfMilksQuantity);
        // Invoke
        boolean result = cart.decrementItem(listOfMilks.get(0).getId());
        boolean result2 = cart.decrementItem(listOfMilks.get(1).getId());
        // Analyze
        assertEquals(true, result);
        assertEquals(true, result2);
    }

    @Test
    public void testToString()
    {
        // Setup
        String username = "Yaro";
        ArrayList<Milk> listOfMilks = new ArrayList<Milk>();
        double[] rating = {4.0};
        listOfMilks.add(new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        listOfMilks.add(new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg"));
        ArrayList<Integer> listOfMilksQuantity = new ArrayList<Integer>();
        listOfMilksQuantity.add(5);
        listOfMilksQuantity.add(4);
        ShoppingCart cart = new ShoppingCart(username, listOfMilks, listOfMilksQuantity);
        String expectedString = String.format("%s %s %s", username, listOfMilks, listOfMilksQuantity);
        String actual = cart.toString();
        assertEquals(actual, expectedString);
    }
}
