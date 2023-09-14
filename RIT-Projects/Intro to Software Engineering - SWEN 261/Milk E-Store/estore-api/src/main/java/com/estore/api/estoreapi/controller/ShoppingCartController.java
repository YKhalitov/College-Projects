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
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Milk;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.persistence.ShoppingCartDAO;

/**
 * Handles the REST API requests for the ShoppingCart resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SpoiledMilk Team
 */

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private static final Logger LOG = Logger.getLogger(ShoppingCartController.class.getName());
    private ShoppingCartDAO shoppingCartDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param shoppingCartDAO The {@link ShoppingCartDAO Shopping Cart Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public ShoppingCartController(ShoppingCartDAO shoppingCartDAO) {
        this.shoppingCartDAO = shoppingCartDAO;
    }

    /**
     * Either adds a new or increments a {@linkplain Milk milk} with the provided milk object and username
     * 
     * @param milk - The {@link Milk milk} to add/increment
     * @param userName - The {@link String userName} name of the user
     * 
     * @return ResponseEntity with created {@link ShoppingCart shoppingCart} object and HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/{userName}")
    public ResponseEntity<ShoppingCart> incrementMilk(@RequestBody Milk milk, @PathVariable String userName) {
        LOG.info("PUT /" + milk.toString() + "/" + userName);
        try {
            shoppingCartDAO.addMilk(milk, userName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new empty {@linkplain ShoppingCart cart} with the provided username
     * 
     * @param userName - The {@link String userName} name of the user
     * 
     * @return ResponseEntity indicating if the creating was successful {@link Boolean result} and HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/{userName}")
    public ResponseEntity<ShoppingCart> createNewCart(@PathVariable String userName) {
        LOG.info("POST /" + "creating" + "/" + userName);
        try {
            ShoppingCart cart = shoppingCartDAO.createNewCart(userName);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes or decrements a {@linkplain Milk milk} with the given id
     * 
     * @param id The id of the {@link Milk milk} to deleted
     * @param userName - The {@link String userName} name of the user
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/{userName}/{id}")
    public ResponseEntity<ShoppingCart> decrementMilk(@PathVariable int id, @PathVariable String userName) {
        LOG.info("PUT /" + id + "/" + userName);
        try {
            shoppingCartDAO.decrementMilk(id, userName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the DELETE request for one {@linkplain ShoppingCart cart}
     * 
     * @return ResponseEntity with HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<ShoppingCart> deleteShoppingCart(@PathVariable String username) {
        LOG.info("DELETE /cart/" + username);
        try {
            shoppingCartDAO.deleteShoppingCart(username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the PUT request for one {@linkplain ShoppingCart cart}
     * 
     * @return ResponseEntity with {@link ShoppingCart cart} object thats empty and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/{username}/empty")
    public ResponseEntity<ShoppingCart> emptyShoppingCart(@PathVariable String username) {
        LOG.info("PUT /empty cart/" + username);
        try {
            ShoppingCart cart = shoppingCartDAO.emptyShoppingCart(username);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Responds to the GET request for all {@linkplain ShoppingCart listOfCarts}
     * 
     * @return ResponseEntity with array of {@link ShoppingCart listOfCarts} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<ShoppingCart[]> getShoppingCarts() {
        LOG.info("GET /carts");
        try {
            ShoppingCart[]  listOfCarts = shoppingCartDAO.getShoppingCarts();
            return new ResponseEntity<>(listOfCarts, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for one {@linkplain ShoppingCart cart}
     * 
     * @return ResponseEntity with array of {@link ShoppingCart cart} object (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{name}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable String name) {
        LOG.info("GET /cart/" + name);
        try {
            ShoppingCart cart = shoppingCartDAO.getShoppingCart(name);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}    
