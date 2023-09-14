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

/**
 * Implements the functionality for JSON file-based peristance for Milk
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty
 */
@Component
public class MilkFileDAO implements MilkDAO {
    Map<Integer,Milk> milks;   // Provides a local cache of the milk objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Milk
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new milk
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Milk File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public MilkFileDAO(@Value("${milk.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the milks from the file
    }

    /**
     * Generates the next id for a new {@linkplain Milk milk}
     * 
     * @return The next id
     */
    private static synchronized int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Milk milks} from the tree map
     * 
     * @return  The array of {@link Milk milks}, may be empty
     */
    private Milk[] getMilkArray() {
        return getMilkArray(null);
    }

    /**
     * Generates an array of {@linkplain Milk milkArrayList} from the tree map for any
     * {@linkplain Milk milks} that contains the text specified by type
     * <br>
     * If type is null, the array contains all of the {@linkplain Milk milks}
     * in the tree map
     * 
     * @return  The array of {@link Milk milkArray}, may be empty
     */
    private Milk[] getMilkArray(String type) { // if type == null, no filter
        ArrayList<Milk> milkArrayList = new ArrayList<>();

        for (Milk milk : milks.values()) {
            if (type == null || milk.getType().contains(type)) {
                milkArrayList.add(milk);
            }
        }

        Milk[] milkArray = new Milk[milkArrayList.size()];
        milkArrayList.toArray(milkArray);
        return milkArray;
    }

    /**
     * Saves the {@linkplain Milk milks} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Milk milks} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Milk[] milkArray = getMilkArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),milkArray);
        return true;
    }

    /**
     * Loads {@linkplain Milk milks} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        milks = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of milkArray
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Milk[] milkArray = objectMapper.readValue(new File(filename),Milk[].class);

        // Add each milk to the tree map and keep track of the greatest id
        for (Milk milk : milkArray) {
            milks.put(milk.getId(),milk);
            if (milk.getId() > nextId)
                nextId = milk.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Milk[] getMilks() {
        synchronized(milks) {
            return getMilkArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Milk[] searchMilks(String type) {
        synchronized(milks) {
            return getMilkArray(type);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Milk getMilk(int id) {
        synchronized(milks) {
            if (milks.containsKey(id))
                return milks.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Milk createMilk(Milk milk) throws IOException {
        synchronized(this.milks) {
            // We create a new milk object because the id field is immutable
            // and we need to assign the next unique id
            Milk newMilk = new Milk(nextId(), milk.getType(), milk.getFlavor(), milk.getVolume(), milk.getQuantity(), milk.getPrice(), milk.getRating(), milk.getCalcRating(), milk.getImageUrl());
            this.milks.put(newMilk.getId(),newMilk);
            save(); // may throw an IOException
            return newMilk;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Milk updateMilk(Milk milk) throws IOException {
        synchronized(milks) {
            if (!milks.containsKey(milk.getId()))
                return null;  // milk does not exist

            milks.put(milk.getId(),milk);
            save(); // may throw an IOException
            return milk;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteMilk(int id) throws IOException {
        synchronized(milks) {
            if (milks.containsKey(id)) {
                milks.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
