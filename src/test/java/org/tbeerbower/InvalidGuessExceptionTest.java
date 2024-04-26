package org.tbeerbower;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvalidGuessExceptionTest {

    private static final String TEST_MESSAGE = "Your guess was not a good one!";
    private static final String TEST_GUESS = "testy";

    private InvalidGuessException exception;

    @Before
    public void setup() {
        exception = new InvalidGuessException(TEST_GUESS, TEST_MESSAGE);
    }

    @Test
    public void getGuess() {
        assertEquals(TEST_GUESS, exception.getGuess());
    }

    @Test
    public void getMessage() {
        assertEquals(TEST_MESSAGE, exception.getMessage());
    }
}