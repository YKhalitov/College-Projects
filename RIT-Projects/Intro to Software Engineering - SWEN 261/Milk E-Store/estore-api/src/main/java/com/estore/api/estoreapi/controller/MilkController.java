package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.MilkDAO;
import com.estore.api.estoreapi.model.Milk;

/**
 * Handles the REST API requests for the Milk resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("milks")
public class MilkController {
    private static final Logger LOG = Logger.getLogger(MilkController.class.getName());
    private MilkDAO milkDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param milkDao The {@link MilkDAO Milk Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public MilkController(MilkDAO milkDao) {
        this.milkDao = milkDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Milk milk} for the given id
     * 
     * @param id The id used to locate the {@link Milk milk}
     * 
     * @return ResponseEntity with {@link Milk milk} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Milk> getMilk(@PathVariable int id) {
        LOG.info("GET /milks/" + id);
        try {
            Milk milk = milkDao.getMilk(id);
            if (milk != null)
                return new ResponseEntity<>(milk,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Milk listOfMilks}
     * 
     * @return ResponseEntity with array of {@link Milk listOfMilks} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Milk[]> getMilks() {
        LOG.info("GET /milks");
        try {
            Milk[] listOfMilks = milkDao.getMilks();
            return new ResponseEntity<>(listOfMilks,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Milk milks} whose name contains
     * the text in type
     * 
     * @param type The type parameter which contains the text used to find the {@link Milk milks}
     * 
     * @return ResponseEntity with array of {@link Milk milks} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/")
    public ResponseEntity<Milk[]> searchMilks(@RequestParam String type) {
        LOG.info("GET /milks/?type="+type);
        try {
            Milk[] milks = milkDao.searchMilks(type);
            return new ResponseEntity<>(milks,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Milk milk} with the provided milk object
     * 
     * @param milk - The {@link Milk milk} to create
     * 
     * @return ResponseEntity with created {@link Milk milk} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Milk milk} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Milk> createMilk(@RequestBody Milk milk) {
        LOG.info("POST /milks " + milk);
        try {
            Milk newMilk = milkDao.createMilk(milk);
            return new ResponseEntity<>(newMilk,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Milk milk} with the provided {@linkplain Milk milk} object, if it exists
     * 
     * @param milk The {@link Milk milk} to update
     * 
     * @return ResponseEntity with updated {@link Milk milk} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Milk> updateMilk(@RequestBody Milk milk) { 
        LOG.info("PUT /milks " + milk);
        try {
            Milk newMilk = milkDao.updateMilk(milk);
            if (newMilk != null)
                return new ResponseEntity<>(newMilk,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Milk milk} with the given id
     * 
     * @param id The id of the {@link Milk milk} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Milk> deleteMilk(@PathVariable int id) {
        LOG.info("DELETE /milks/" + id);
        try {
            boolean deleted = milkDao.deleteMilk(id);
            if (deleted)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
