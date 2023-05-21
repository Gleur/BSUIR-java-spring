package com.example.mathservice.MathserviceControlletTest;

import com.example.mathservice.counter.Counter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CounterTest {

    @Test
    public void testCounter() throws InterruptedException {
        Counter counter = new Counter();
        int numThreads = 10;
        int numIncrements = 10000;
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < numIncrements; j++) {
                    Counter.increment();
                }
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(numThreads * numIncrements, counter.getCounter());
    }
}
