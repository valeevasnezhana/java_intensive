package edu.school21.numbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    private NumberWorker numberWorker;

    @BeforeEach
    void setUp() {
        numberWorker = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 19, 271, 3571, 35317, 2147483647})
    void isPrimeForPrimes(int number) throws IllegalNumberException {
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 800000000})
    void isPrimeForNotPrimes(int number) throws IllegalNumberException {
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -5})
    void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class,
                () -> numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int number, int expectedSum) {
        int actualSum = numberWorker.digitsSum(number);
        assertEquals(expectedSum, actualSum);
    }
}
