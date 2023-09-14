package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Milk entity
 * 
 * @author SWEN Faculty
 * @author Jeremy Smart
 */
public class Milk {
    // Package private for tests
    static final String STRING_FORMAT = "Milk [id=%d, name=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("type") private String type;
    @JsonProperty("flavor") private String flavor;
    @JsonProperty("volume") private double volume;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("price") private double price;
    @JsonProperty("rating") private double[] rating;
    @JsonProperty("calcRating") private double calcRating;
    @JsonProperty("imageUrl") String imageUrl;


    /**
     * Create a milk with the given id and name
     * @param id The id of the milk
     * @param name The name of the milk
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Milk(@JsonProperty("id") int id, @JsonProperty("type") String type, @JsonProperty("flavor") String flavor, @JsonProperty("volume") double volume, @JsonProperty("quantity") int quantity, @JsonProperty("price") double price, @JsonProperty("rating") double[] rating, @JsonProperty("calcRating") double calcRating, @JsonProperty("imageUrl") String imageUrl) {
        this.id = id;
        this.type = type;
        this.flavor = flavor;
        this.volume = volume;
        this.quantity = quantity;
        this.price = price;
        this.rating = rating;
        this.calcRating = calcRating;
        this.imageUrl = imageUrl;
    }

    /**
     * Retrieves the id of the milk
     * @return The id of the milk
     */
    public int getId() {return id;}

    /**
     * Sets the type of the milk - necessary for JSON object to Java object deserialization
     * @param type The type of the milk
     */
    public void setType(String type) {this.type = type;}

    /**
     * Retrieves the type of the milk
     * @return The type of the milk
     */
    public String getType() {return type;}

    /**
     * Sets the flavor of the milk - necessary for JSON object to Java object deserialization
     * @param name The flavor of the milk
     */
    public void setFlavor(String flavor) {this.flavor = flavor;}

    /**
     * Retrieves the flavor of the milk
     * @return The flavor of the milk
     */
    public String getFlavor() {return flavor;}

    /**
     * Sets the volume of the milk - necessary for JSON object to Java object deserialization
     * @param name The volume of the milk
     */
    public void setVolume(double volume) {this.volume = volume;}

    /**
     * Retrieves the volume of the milk
     * @return The volume of the milk
     */
    public double getVolume() {return volume;}

    /**
     * Sets the quantity of the milk - necessary for JSON object to Java object deserialization
     * @param name The quantity of the milk
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * Retrieves the quantity of the milk
     * @return The quantity of the milk
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the price of the milk - necessary for JSON object to Java object deserialization
     * @param name The price of the milk
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * Retrieves the price of the milk
     * @return The price of the milk
     */
    public double getPrice() {return price;}

    /**
     * Adds a rating to the rating array of the milk - necessary for JSON object to Java object deserialization
     * @param rating The rating of the milk
     */
    public void addRating(double rating) {
        double[] newRatings = new double[this.rating.length + 1];
        System.arraycopy(this.rating, 0, newRatings, 0, this.rating.length);
        newRatings[this.rating.length] = rating;
        this.rating = newRatings;
    }

    /**
     * Clears the rating array of the milk - necessary for JSON object to Java object deserialization
     */
    public void clearRating() {this.rating = new double[0];}

    /**
     * Retrieves the rating array of the milk
     * @return The list of ratings of the milk
     */
    public double[] getRating() {return this.rating;}

    /**
     * Calculates the rating of the milk.
     * Sets the calculated rating of the milk - necessary for JSON object to Java object deserialization
     */
    public void calcRating() {
        double sum = 0;

        for (int i = 0; i < rating.length; i++) {
            sum += rating[i];
        }

        this.calcRating = sum/rating.length;
    }

    /**
     * Retrieves the calculated rating of the milk
     * @return The calculated rating of the milk
     */
    public double getCalcRating() {return this.calcRating;}

    /**
     * Sets the image url of the milk - necessary for JSON object to Java object deserialization
     * @param imageUrl The image url of the milk
     */
    public void setURL(String imageUrl) {this.imageUrl = imageUrl;}

    /**
     * Retrieves the image url of the milk
     * @return The image url of the milk
     */
    public String getImageUrl() {return imageUrl;}


    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object instanceof Milk)
        {
            sameSame = this.id == ((Milk) object).id;
        }

        return sameSame;
    }

    @Override
    public int hashCode() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,type);
    }
}