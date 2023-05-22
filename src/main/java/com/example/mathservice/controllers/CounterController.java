package com.example.mathservice.controllers;


import com.example.mathservice.counter.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CounterController {

    Logger logger = LoggerFactory.getLogger(CounterController.class);

    @GetMapping("/counter")
    public ResponseEntity counterService() {
        logger.info("Get counter");
        return ResponseEntity.ok("counter: " + Counter.getCounter());
    }
}
