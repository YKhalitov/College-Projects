package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.persistence.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Milk Controller class
 * 
 * @author Spoiled Milk Team
 */
@Tag("Controller-tier")
public class MilkControllerTest {
    private MilkController milkController;
    private MilkDAO mockMilkDAO;

    /**
     * Before each test, create a new MilkController object and inject
     * a mock Milk DAO
     */
    @BeforeEach
    public void setupMilkController() {
        mockMilkDAO = mock(MilkDAO.class);
        milkController = new MilkController(mockMilkDAO);
    }

    @Test
    public void testGetMilk() throws IOException {  // getMilk may throw IOException
        // Setup
        double[] rating = {4.0};
        Milk milk = new Milk(1,"Oat", "Dragonfruit", 35.0, 50, 99.99, rating, 4.0, "");
        
        // When the same id is passed in, our mock Milk DAO will return the Milk object
        when(mockMilkDAO.getMilk(milk.getId())).thenReturn(milk);

        // Invoke
        ResponseEntity<Milk> response = milkController.getMilk(milk.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(milk,response.getBody());
    }

    @Test
    public void testGetMilkNotFound() throws Exception { // createMilk may throw IOException
        // Setup
        int milkID = 99;
        // When the same id is passed in, our mock Milk DAO will return null, simulating
        // no milk found
        when(mockMilkDAO.getMilk(milkID)).thenReturn(null);

        // Invoke
        ResponseEntity<Milk> response = milkController.getMilk(milkID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetMilkHandleException() throws Exception { // createMilk may throw IOException
        // Setup
        int milkID = 99;
        // When getMilk is called on the Mock Milk DAO, throw an IOException
        doThrow(new IOException()).when(mockMilkDAO).getMilk(milkID);

        // Invoke
        ResponseEntity<Milk> response = milkController.getMilk(milkID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteMilk() throws IOException { // deleteMilk may throw IOException
        // Setup
        int milkID = 99;
        // when deleteMilk is called return true, simulating successful deletion
        when(mockMilkDAO.deleteMilk(milkID)).thenReturn(true);

        // Invoke
        ResponseEntity<Milk> response = milkController.deleteMilk(milkID);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteMilkNotFound() throws IOException { // deleteMilk may throw IOException
        // Setup
        int milkID = 99;
        // when deleteMilk is called return false, simulating failed deletion
        when(mockMilkDAO.deleteMilk(milkID)).thenReturn(false);

        // Invoke
        ResponseEntity<Milk> response = milkController.deleteMilk(milkID);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteMilkHandleException() throws IOException { // deleteMilk may throw IOException
        // Setup
        int milkID = 99;
        // When deleteMilk is called on the Mock Milk DAO, throw an IOException
        doThrow(new IOException()).when(mockMilkDAO).deleteMilk(milkID);

        // Invoke
        ResponseEntity<Milk> response = milkController.deleteMilk(milkID);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetMilks() throws IOException
    {
        // Setting up
        Milk[] milks = new Milk[2];
        double[] rating = {4.0};
        milks[0] = new Milk(25, "cow", "banana", 2.4, 10, 2.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        milks[1] = new Milk(26, "goat", "peach", 5.8, 6, 6.24, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // When getMilks is called return the milks created above
        when(mockMilkDAO.getMilks()).thenReturn(milks);
        ResponseEntity<Milk[]> response = milkController.getMilks();
        // Invoke
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Analyze
        assertEquals(milks, response.getBody());
    }

    @Test
    public void testGetMilksHandleException() throws IOException
    {
        // throw an IOException when getMilks is called on Mpck Milk DAO
        doThrow(new IOException()).when(mockMilkDAO).getMilks();
        //Invoke
        ResponseEntity<Milk[]> response = milkController.getMilks();
        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchMilks() throws IOException { // searchMilks may throw IOException
        // Setup
        String searchString = "oa";
        double[] rating = {4.0};
        Milk[] milks = new Milk[2];
        milks[0] = new Milk(27, "goat", "orange", 2.5, 4, 4.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        milks[1] = new Milk(28, "oat", "vanilla", 3.6, 8, 3.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // When searchMilks is called with the search string, return the 
        // two milks above
        when(mockMilkDAO.searchMilks(searchString)).thenReturn(milks);

        // Invoke
        ResponseEntity<Milk[]> response = milkController.searchMilks(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(milks, response.getBody());
    }

    @Test
    public void testSearchMilksHandleException() throws IOException { // searchMilks may throw IOException
        // Setup
        String searchString = "ow";
        // When createMilk is called on the Mock Milk DAO, throw an IOException
        doThrow(new IOException()).when(mockMilkDAO).searchMilks(searchString);

        // Invoke
        ResponseEntity<Milk[]> response = milkController.searchMilks(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testCreateMilk() throws IOException { //createMilk may throw IOException
        // Setup
        double[] rating = {4.0};
        Milk milk = new Milk(0, "cow", "strawberry", 0.25, 10, 6.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // when createMilk is called return true, simulating successful deletion
        when(mockMilkDAO.createMilk(milk)).thenReturn(milk);

        // Invoke
        ResponseEntity<Milk> response = milkController.createMilk(milk);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(milk,response.getBody());
    }

    @Test
    public void testCreateMilkHandleException() throws IOException
    {
        // Setup
        double[] rating = {4.0};
        Milk milk = new Milk(30, "coconut", "wheat", 5.9, 2, 7.3, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        // throw an IOException when createHero is called on mockMilkDAO
        doThrow(new IOException()).when(mockMilkDAO).createMilk(milk);
        // Invoke
        ResponseEntity<Milk> response = milkController.createMilk(milk);
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testUpdateMilk() throws IOException {
        // Setup
        double[] rating = {4.0};
        Milk milk = new Milk(0, "almond", "chocolate", 0.5, 10, 8.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        
        // when updateMilk is called, return milk, simulating the successful update
        when(mockMilkDAO.updateMilk(milk)).thenReturn(milk);

        // Invoke
        ResponseEntity<Milk> response = milkController.updateMilk(milk);
        
        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(milk,response.getBody());
    }

    @Test
    public void testUpdateMilkNotFound() throws IOException {
        // Setup
        double[] rating = {4.0};
        Milk milk = new Milk(99, "almond", "chocolate", 0.5, 10, 8.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        
        // when updateMilk is called, return null, simulating the failed update
        when(mockMilkDAO.updateMilk(milk)).thenReturn(null);

        // Invoke
        ResponseEntity<Milk> response = milkController.updateMilk(milk);
        
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateMilkHandleException() throws IOException {
        // Setup
        double[] rating = {4.0};
        Milk milk = new Milk(9, "almond", "chocolate", 0.5, 10, 8.99, rating, 4.0, "../assets/images/glass-o-milk.jpg");
        
        // Throw an IOException when updateMilk is called
        doThrow(new IOException()).when(mockMilkDAO).updateMilk(milk);

        // Invoke
        ResponseEntity<Milk> response = milkController.updateMilk(milk);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
