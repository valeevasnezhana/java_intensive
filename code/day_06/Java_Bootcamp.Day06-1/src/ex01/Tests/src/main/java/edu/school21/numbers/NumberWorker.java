package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) throws IllegalNumberException {
        if (number <= 1) {
            throw new IllegalNumberException("Error: number must be greater than 1");
        }
        boolean isPrime = true;
        if (number != 2 && number != 3) {
            if (number % 2 == 0 || number % 3 == 0) {
                isPrime = false;
            } else {
                for (int i = 5; i <= Math.sqrt(number) + 1; i += 2) {
                    if (number % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }
        }
        return isPrime;
    }

    public int digitsSum(int number) {
        int result = 0;
        result += number % 10;
        while (number > 10) {
            number /= 10;
            result += number % 10;
        }
        return result;
    }
}
