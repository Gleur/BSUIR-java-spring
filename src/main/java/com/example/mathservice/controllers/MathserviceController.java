package com.example.mathservice.controllers;

import com.example.mathservice.cache.Cache;
import com.example.mathservice.counter.Counter;
import com.example.mathservice.counter.CounterThread;
import com.example.mathservice.models.Validator;
import com.example.mathservice.models.Vector;
import com.example.mathservice.models.VectorService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathserviceController {

    private Cache cache;
    @Autowired
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    Logger logger = LoggerFactory.getLogger(MathserviceController.class);
    VectorService vectorService = new VectorService();
    Validator validator = new Validator();

    @GetMapping("/vector")
    public ResponseEntity vectorService(@RequestParam("X1") double x1,
                                @RequestParam("Y1") double y1,
                                @RequestParam("X2") double x2,
                                @RequestParam("Y2") double y2) throws IllegalArgumentException, JSONException {

        CounterThread counter = new CounterThread();
        counter.run();
        logger.info("Started successfully");
        logger.info("Received request with parameters: x1={}, y1={}, x2={}, y2={}", x1, y1, x2, y2);
        String cacheKey = String.format("%f_%f_%f_%f", x1, y1, x2, y2);
        String cachedResult = cache.get(cacheKey);
        if(cachedResult != null) {
            logger.info("Result found in cache");
            //return ResponseEntity.ok("result from cache="+cachedResult+"  counter="+Counter.getCounter());
           // return new ResponseEntity<>("Result: " + Counter.getCounter() + "(iter), " + results, httpstatus.ok")
            return ResponseEntity.ok(cachedResult + " (from cache), counter: " + Counter.getCounter());
            //  return cachedResult;
            //return new ResponseEntity<>("Result: " + Counter.getCounter() + ", " + cachedResult.toString(), HttpStatus.OK);

        }
        Vector vector = new Vector(x1, y1, x2, y2);
        logger.info("x1 validation");
        validator.validate(vector.getX1());
        logger.info("VectorService initialization");
        logger.info("Output started");

        JSONObject result = new JSONObject();
        result.put("VectorNorm: ", vectorService.calculateNorm(vector));
        result.put("X projection: ", vectorService.calculateXProjection(vector));
        result.put("Y projection: ", vectorService.calculateYProjection(vector));
       // result.put("Counter: ", Counter.getCounter());

        String resultString = result.toString();

        cache.put(cacheKey, resultString);
        logger.info("Result cached with key: {}", cacheKey);

        return ResponseEntity.ok(result + ", counter: " + Counter.getCounter());
    }

}
//http://localhost:8080/vector?X1=10.0&Y1=0.0&X2=3.0&Y2=4.0