package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class MilkTest {
    @Test
    public void testMilk()
    {
        // Setup
        String expectedType = "cow";
        String expectedFlavor = "banana";
        double expectedVolume = 4.7;
        int expectedQuantity = 6;
        double expectedPrice = 4.85;
        double[] expectedRating = {4.0, 5.0};
        double expectedCalcRating = 4.5;
        String expectedImageUrl = "../assets/images/glass-o-milk.jpg";
        // Invoke
        Milk milk = new Milk(10, expectedType, expectedFlavor, expectedVolume, expectedQuantity, expectedPrice, expectedRating, expectedCalcRating, expectedImageUrl);
        // Analyze
        assertEquals(expectedType, milk.getType());
        assertEquals(expectedFlavor, milk.getFlavor());
        assertEquals(expectedVolume, milk.getVolume());
        assertEquals(expectedQuantity, milk.getQuantity());
        assertEquals(expectedRating, milk.getRating());
        assertEquals(expectedCalcRating, milk.getCalcRating());
        assertEquals(expectedPrice, milk.getPrice());
    }

    @Test
    public void testSetType()
    {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        String expectedType = "cow";
        // Invoke
        milk.setType(expectedType);
        // Analyze
        assertEquals(expectedType, milk.getType());
    }

    @Test
    public void testSetFlavor()
    {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        String expectedFlavor = "cow";
        // Invoke
        milk.setFlavor(expectedFlavor);
        // Analyze
        assertEquals(expectedFlavor, milk.getFlavor());
    }

    @Test
    public void testSetVolume()
    {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        double expectedVolume = 6.1;
        // Invoke
        milk.setVolume(expectedVolume);
        // Analyze
        assertEquals(expectedVolume, milk.getVolume());
    }

    @Test
    public void testSetQuantity()
    {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        int expectedQuantity = 5;
        // Invoke
        milk.setQuantity(expectedQuantity);
        // Analyze
        assertEquals(expectedQuantity, milk.getQuantity());
    }

    @Test
    public void testSetPrice()
    {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        double expectedPrice = 5.99;
        // Invoke
        milk.setPrice(expectedPrice);
        // Analyze
        assertEquals(expectedPrice, milk.getPrice());
    }

    @Test
    public void testAddRating()
    {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        double[] expectedRatings = {4.0, 5.0};
        double addedRating = 5.0;
        // Invoke
        milk.addRating(addedRating);
        // Analyze
        assertArrayEquals(expectedRatings, milk.getRating());
    }

    @Test
    public void testClearRating()
    {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        double[] expectedRatings = {};
        // Invoke
        milk.clearRating();
        // Analyze
        assertArrayEquals(expectedRatings, milk.getRating());
    }

    @Test
    public void testCalcRating()
    {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0, 5.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        double expectedCalcRating = 4.5;
        // Invoke
        milk.calcRating();
        // Analyze
        assertEquals(expectedCalcRating, milk.getCalcRating());
    }

    

    @Test
    public void testSetURL()
    {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        String expectedURL = "../assets/images/glass-o-milk.png";
        // Invoke
        milk.setURL(expectedURL);
        // Analyze
        assertEquals(expectedURL, milk.getImageUrl());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String type = "goat";
        String flavor = "vanilla";
        double volume = 4.5;
        int quantity = 7;
        double price = 4.99;
        double[] rating = {4.0};
        double calcRating = 4.0;
        String imageUrl = "../assets/images/glass-o-milk.jpg";
        String expected_string = String.format(Milk.STRING_FORMAT, id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);
        Milk milk = new Milk(id, type, flavor, volume, quantity, price, rating, calcRating, imageUrl);

        // Invoke
        String actual_string = milk.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
    
}
