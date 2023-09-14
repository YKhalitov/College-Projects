package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Milk;

/**
 * Defines the interface for Milk object persistence
 * 
 * @author SWEN Faculty
 */
public interface MilkDAO {
    /**
     * Retrieves all {@linkplain Milk milks}
     * 
     * @return An array of {@link Milk milk} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Milk[] getMilks() throws IOException;

    /**
     * Finds all {@linkplain Milk milks} whose name contains the given text
     * 
     * @param type The type parameter which contains the text used to find the {@link Milk milks}
     * 
     * @return An array of {@link Milk milks} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Milk[] searchMilks(String type) throws IOException;

    /**
     * Retrieves a {@linkplain Milk milk} with the given id
     * 
     * @param id The id of the {@link Milk milk} to get
     * 
     * @return a {@link Milk milk} object with the matching id
     * <br>
     * null if no {@link Milk milk} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Milk getMilk(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Milk milk}
     * 
     * @param milk {@linkplain Milk milk} object to be created and saved
     * <br>
     * The id of the milk object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Milk milk} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Milk createMilk(Milk milk) throws IOException;

    /**
     * Updates and saves a {@linkplain Milk  milk}
     * 
     * @param {@link Milk milk} object to be updated and saved
     * 
     * @return updated {@link Milk milk} if successful, null if
     * {@link Milk milk} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Milk updateMilk(Milk milk) throws IOException;

    /**
     * Deletes a {@linkplain Milk milk} with the given id
     * 
     * @param id The id of the {@link Milk milk}
     * 
     * @return true if the {@link Milk milk} was deleted
     * <br>
     * false if milk with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteMilk(int id) throws IOException;
}
