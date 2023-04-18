package com.example.mathservice.counter;

import org.springframework.stereotype.Component;

@Component
public class CounterThread extends Thread{
    @Override
    public void run() {
        Counter.increment();
    }
}
