package com.estore.api.estoreapi.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
/**
 * Represents a Milk entity
 * @author SpoiledMilk Team
 */
public class ShoppingCart {
    @JsonProperty("username") private String username;
    @JsonProperty("milksInCart") private List<Milk> milksInCart;
    @JsonProperty("milksInCartQuantity") private List<Integer> milksInCartQuantity;

    /**
     * Create a ShoppingCart with the given username and list of milks and quantities
     * @param username The username of the user
     * @param milksInCart A list of the all the milks in the shopping cart
     * @param milksInCartQuantity A list of the all quantities of the milks in the shopping cart
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public ShoppingCart(@JsonProperty("username") String username,  @JsonProperty("milksInCart") List<Milk> milksInCart,@JsonProperty("milksInCartQuantity") List<Integer> milksInCartQuantity) {
        this.username = username;
        this.milksInCart = milksInCart;
        this.milksInCartQuantity = milksInCartQuantity;
    }

    /**
     * Sets the username of the ShoppingCart - necessary for JSON object to Java object deserialization
     * @param username The username of the ShoppingCart
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Retrieves the username of the ShoppingCart
     * @return The username of the ShoppingCart
     */
    public String getUsername() {return username;}

    /**
     * Sets the list of milks in cart - necessary for JSON object to Java object deserialization
     * @param milksInCart A list of milk objects
     */
    public void setMilksInCart(ArrayList<Milk> milksInCart) {this.milksInCart = milksInCart;}

    /**
     * Sets a milk in the ShoppingCart at a particular index - necessary for JSON object to Java object deserialization
     * @param index Index at which to set the milk object
     * @param milk Milk object to set a specified index
     */
    public void setMilksInCart(int index, Milk milk) {this.milksInCart.set(index, milk);}

    /**
     * Retrieves the list of milks in the ShoppingCart
     * @return The list of milks in the ShoppingCart
     */
    public List<Milk> getMilksInCart() {return milksInCart;}

    /**
     * Retrieves a milk in the ShoppingCart at a specific index
     * @return Milk object from specified index
     */
    public Milk getMilksInCart(int index) {return milksInCart.get(index);}

    /**
     * Sets the list of milk quantities in cart - necessary for JSON object to Java object deserialization
     * @param milksInCart A list of integers representing quantities
     */
    public void setMilksInCartQuantity(ArrayList<Integer> milksInCartQuantity) {this.milksInCartQuantity = milksInCartQuantity;}

    /**
     * Sets a quantity of a milk in the ShoppingCart at a particular index - necessary for JSON object to Java object deserialization
     * @param index Index at which to set the quantity integer
     * @param amount The specific amount to set it to
     */
    public void setMilksInCartQuantity(int index, int amount) {this.milksInCartQuantity.set(index, amount);}

    /**
     * Retrieves the list of milk quantities in the ShoppingCart
     * @return The list of milk quantities in the ShoppingCart
     */
    public List<Integer> getMilksInCartQuantity() {return milksInCartQuantity;}

    /**
     * Retrieves a milk quantity in the ShoppingCart at a specific index
     * @return Milk quantity from specified index
     */
    public Integer getMilksInCartQuantity(int index) {return milksInCartQuantity.get(index);}

    /**
     * Either creates or increments a specified milk in the ShoppingCart
     * @param Milk The milk that needs to be incremented or created
     */
    public boolean incrementItem(Milk milk) {
        synchronized(this){
            if(milksInCart.contains(milk)){
                milksInCartQuantity.set(milksInCart.indexOf(milk), milksInCartQuantity.get(milksInCart.indexOf(milk)) + 1);
                //increment in cart also
                Milk temp = milksInCart.get(milksInCart.indexOf(milk));
                temp.setQuantity(temp.getQuantity() - 1);
                milksInCart.set(milksInCart.indexOf(milk), temp);
            }else{
                milksInCart.add(milk);
                milksInCartQuantity.add(1);
            }
            return true;
        }  
    }

    /**
     * Either deletes or decrements a specified milk in the ShoppingCart based on the id
     * @param id The id of the milk that needs to be deleted or decremented
     */
    public boolean decrementItem(int id) {
        synchronized(this){
            int indexCounter = 0;
            for (Milk milk : milksInCart) {
                if (milk.getId() == id) {
                    if(milksInCartQuantity.get(indexCounter) >= 2){
                        milksInCartQuantity.set(indexCounter, milksInCartQuantity.get(indexCounter) - 1);
                        //decrement in cart also
                        Milk temp = milksInCart.get(indexCounter);
                        temp.setQuantity(temp.getQuantity() + 1);
                        milksInCart.set(indexCounter, temp);
                    }
                    else{
                        milksInCartQuantity.remove(indexCounter);
                        milksInCart.remove(indexCounter);
                    }   
                        break;
                    }    
                    indexCounter++;
                }
            }
            return true;
        }

    /**
     * Empties the milkInCart and milkInCartQuantity arrays and returns the new object.
     */
    public ShoppingCart empty(){
        this.milksInCart.removeAll(milksInCart);
        this.milksInCartQuantity.removeAll(milksInCartQuantity);
        return this;
    }    
    

    /**
     * Creates a string representation of the values stored in this ShoppingCart
     * @return String representation of the values stored in this ShoppingCart
     */
    @Override
    public String toString() {
        return  (username + " " + milksInCart.toString() + " " + milksInCartQuantity.toString());
    }
    
}
