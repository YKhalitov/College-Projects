package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")

public class HashedLoginTest {
    @Test
    public void testEquals()
    {
        HashedLogin login1 = new HashedLogin("jeremy", "jeremy");
        HashedLogin login2 = new HashedLogin("jeremy", "jeremy");
        HashedLogin login3 = new HashedLogin("yaro", "hello");
        boolean result = login1.equals(login2);
        boolean result2 = login1.equals(login3);
        assertEquals(true, result);
        assertEquals(false, result2);
    }
    
}
