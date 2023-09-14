package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Milk;
import com.estore.api.estoreapi.model.ShoppingCart;

/**
 * Defines the interface for ShoppingCart object persistence
 * 
 * @author SWEN Faculty
 */
public interface ShoppingCartDAO {
    /**
     * Adds/increments a {@linkplain Milk milk} to the ShoppingCart with the matching {@linkplain String username}
     * 
     * @param milk The {@linkplain Milk milk} that will be either added or incremented to the ShoppingCart
     * @param userName The {@linkplain String usename} that is used to identify which cart to add {@linkplain Milk milk} object to
     * 
     * @throws IOException if an issue with underlying storage
     */
    boolean addMilk(Milk milk, String userName) throws IOException;

    /**
     * Deletes/decrements a {@linkplain Milk milk} from the the ShoppingCart with the matching {@linkplain String username}
     * 
     * @param id The {@linkplain Integer id} that is used to identify which milk to delete/decrement
     * @param userName The {@linkplain String usename} that is used to identify which cart to delete/decrement the {@linkplain Milk milk}
     *  object derived from the {@linkplain Integer id}
     * 
     * @throws IOException if an issue with underlying storage
     */
    boolean decrementMilk(int id, String userName) throws IOException;

    /**
     * Retrieves all {@linkplain ShoppingCart cart}
     * 
     * @return An array of {@link ShoppingCart cart} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart[] getShoppingCarts() throws IOException;

    /**
     * Retrieves a {@linkplain ShoppingCart cart} with the given userName
     * 
     * @param userName The userName of the {@link ShoppingCart cart} to get
     * 
     * @return a {@link ShoppingCart cart} object with the matching userName
     * <br>
     * null if no {@link ShoppingCart cart} with a matching userName is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart getShoppingCart(String userName) throws IOException;

    /**
     * Creates a {@linkplain ShoppingCart cart} with the given userName
     * 
     * @param userName The userName of the {@link ShoppingCart cart} to get
     * 
     * @return a {@link ShoppingCart cart} object with the matching userName
     * <br>
     * null if no {@link ShoppingCart cart} with a matching userName is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart createNewCart(String userName) throws IOException;

    /**
     * Empties the {@linkplain ShoppingCart cart} with the given userName
     * 
     * @param userName The userName of the {@link ShoppingCart cart} to get
     * 
     * @return a {@link ShoppingCart cart} object with the matching userName
     * <br>
     * null if no {@link ShoppingCart cart} with a matching userName is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    ShoppingCart emptyShoppingCart(String userName) throws IOException;

    /**
     * Deletes the {@linkplain ShoppingCart cart} with the given userName
     * 
     * @param userName The userName of the {@link ShoppingCart cart} to get
     * 
     * @throws IOException if an issue with underlying storage
     */
    void deleteShoppingCart(String userName) throws IOException;
}    