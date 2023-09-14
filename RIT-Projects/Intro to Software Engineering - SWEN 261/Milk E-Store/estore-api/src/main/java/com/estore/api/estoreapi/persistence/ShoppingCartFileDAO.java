package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Milk;
import com.estore.api.estoreapi.model.ShoppingCart;

/**
 * Implements the functionality for JSON file-based persistance for ShoppingCart
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author SpoiledMilk Team
 */
@Component
public class ShoppingCartFileDAO implements ShoppingCartDAO {
    Map<String,ShoppingCart> shoppingCart;   // Provides a local cache of the milk objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Milk
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to


    /**
     * Creates a ShoppingCart File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ShoppingCartFileDAO(@Value("${shoppingCart.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the ShoppingCart from the file
    }

    /**
     * Generates an array of {@linkplain ShoppingCart carts} from the tree map
     * 
     * @return  The array of {@link ShoppingCart carts}, may be empty
     */
    private ShoppingCart[] getShoppingCartArray() {
        return getShoppingCartArray(null);
    }

    /**
     * Generates an array of {@linkplain ShoppingCart shoppingCartArrayList} from the tree map for any
     * {@linkplain ShoppingCart carts} that contains the text specified by type
     * <br>
     * If type is null, the array contains all of the {@linkplain ShoppingCart carts}
     * in the tree map
     * 
     * @return  The array of {@linkplain ShoppingCart shoppingCartArray}, may be empty
     */
    private ShoppingCart[] getShoppingCartArray(String type) { // if type == null, no filter
        ArrayList<ShoppingCart> shoppingCartArrayList = new ArrayList<>();
       for (ShoppingCart cart : shoppingCart.values()) {
            if (type == null || cart.getUsername().contains(type)) {
                shoppingCartArrayList.add(cart);
            }
        }
        ShoppingCart[] shoppingCartArray = new ShoppingCart[shoppingCartArrayList.size()];
        shoppingCartArrayList.toArray(shoppingCartArray);
        return shoppingCartArray;
    }

     /**
    ** {@inheritDoc}
     */
    @Override
    public ShoppingCart getShoppingCart(String name) {
        synchronized(shoppingCart) {
            if (shoppingCart.containsKey(name))
                return shoppingCart.get(name);
            else
                return null;
        }
    }

    /**
     * Saves the {@linkplain ShoppingCart carts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link ShoppingCart carts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        ShoppingCart[] shoppingCartArray = getShoppingCartArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),shoppingCartArray);
        return true;
    }

    /**
     * Loads {@linkplain ShoppingCart carts} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        shoppingCart = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of milkArray
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        ShoppingCart[] shoppingCartArray = objectMapper.readValue(new File(filename),ShoppingCart[].class);

        // Add each milk to the tree map and keep track of the greatest id
        for (ShoppingCart cart : shoppingCartArray) {
            shoppingCart.put(cart.getUsername(), cart);
        }
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public ShoppingCart[] getShoppingCarts() throws IOException {
        synchronized(shoppingCart) {
            return getShoppingCartArray();
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean addMilk(Milk milk, String userName) throws IOException {
        synchronized(shoppingCart) {
            // We create a new milk object because the id field is immutable
            // and we need to assign the next unique id
            shoppingCart.get(userName).incrementItem(milk);
            save(); // may throw an IOException
            return true;
        }        
    }

    /**
     * {@inheritDoc}
     */
    public boolean decrementMilk(int id, String userName) throws IOException {
        synchronized(shoppingCart) {
            if (shoppingCart.containsKey(userName)){
                shoppingCart.get(userName).decrementItem(id);
                save();
            }
            return true;
        }    
    }

    /**
     * {@inheritDoc}
     */
    public ShoppingCart createNewCart(String userName) throws IOException{
        synchronized(shoppingCart){
            ArrayList<Milk> tempMilkList = new ArrayList<>();
            ArrayList<Integer> tempMilkQuantityList = new ArrayList<>();
            ShoppingCart newCart = new ShoppingCart(userName, tempMilkList, tempMilkQuantityList);
            shoppingCart.put(userName, newCart);
            save();
            return newCart;
            
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public ShoppingCart emptyShoppingCart(String userName) throws IOException{
        synchronized(shoppingCart){
            ShoppingCart newCart = shoppingCart.get(userName).empty();
            save();
            return newCart;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void deleteShoppingCart(String userName) throws IOException{
        synchronized(shoppingCart){
            shoppingCart.remove(userName);
            save();
        }
    }

}    