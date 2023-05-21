package com.example.mathservice.controllers;

import com.example.mathservice.cache.Cache;
import com.example.mathservice.counter.Counter;
import com.example.mathservice.counter.CounterThread;
import com.example.mathservice.models.SaveVector;
import com.example.mathservice.models.Validator;
import com.example.mathservice.models.Vector;
import com.example.mathservice.models.VectorService;
import com.example.mathservice.repos.SaveVectorRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class MathserviceController {

    private Cache cache;
    @Autowired
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Autowired
    private SaveVectorRepository saveVectorRepository;


    Logger logger = LoggerFactory.getLogger(MathserviceController.class);
    VectorService vectorService = new VectorService();
    Validator validator = new Validator();

    @RequestMapping (value = "/vector",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity vectorService(@RequestParam("X1") double x1,
                                @RequestParam("Y1") double y1,
                                @RequestParam("X2") double x2,
                                @RequestParam("Y2") double y2) throws IllegalArgumentException, JSONException {
        CounterThread counter = new CounterThread();
        counter.start();
        logger.info("Started successfully");
        logger.info("Received request with parameters: x1={}, y1={}, x2={}, y2={}", x1, y1, x2, y2);
        String cacheKey = String.format("%f_%f_%f_%f", x1, y1, x2, y2);
        String cachedResult = cache.get(cacheKey);
        if (cachedResult != null) {
            logger.info("Result found in cache");
            return ResponseEntity.ok(cachedResult + " (from cache), counter: " + Counter.getCounter());
        }
        Vector vector = new Vector(x1, y1, x2, y2);
        logger.info("x1 validation");
        validator.validate(vector.getX1());
        logger.info("VectorService initialization");
        logger.info("Output started");

        SaveVector saveVector = new SaveVector();

        saveVector.setNorm(vectorService.calculateNorm(vector));
        saveVector.setXproj(vectorService.calculateXProjection(vector));
        saveVector.setYproj(vectorService.calculateYProjection(vector));



        JSONObject result = new JSONObject();
        result.put("VectorNorm: ", vectorService.calculateNorm(vector));
        result.put("X projection: ", vectorService.calculateXProjection(vector));
        result.put("Y projection: ", vectorService.calculateYProjection(vector));

        String resultString = result.toString();

        cache.put(cacheKey, resultString);
        logger.info("Result cached with key: {}", cacheKey);

        saveVectorRepository.save(saveVector);

        return ResponseEntity.ok(result + ", counter: " + Counter.getCounter());
    }
//[    {        "X1": 0,        "Y1": 0,        "X2": 3,        "Y2": 4    },    {        "X1": 0,        "Y1": 2,        "X2": 5,        "Y2": 7    },    {        "X1": 0,        "Y1": 3,        "X2": 1,        "Y2": 8    }]


    @RequestMapping(value = "/bulk",
            method = RequestMethod.POST,
            produces = "application/json")
    public String bulkService(@RequestBody List<Map<String, Double>> params) throws JSONException {
        logger.info("Started bulk request");
        logger.info("Received {} parameters", params.size());

        List<Double> norms = new ArrayList<>();
        List<Double> Xpros = new ArrayList<>();
        List<Double> Ypros = new ArrayList<>();

        for (Map<String, Double> solution : params) {
            Double x1 = solution.get("X1");
            Double y1 = solution.get("Y1");
            Double x2 = solution.get("X2");
            Double y2 = solution.get("Y2");

            String cacheKey = String.format("%f_%f_%f_%f", x1, y1, x2, y2);
            String cachedResult = cache.get(cacheKey);
            if (cachedResult != null) {
                logger.info("Result found in cache");
                //      return ResponseEntity.ok(cachedResult + " (from cache), counter: " + Counter.getCounter());
            }
            Vector vector = new Vector(x1, y1, x2, y2);
            logger.info("VectorService initialization");



            logger.info("Adding to lists");
            norms.add(vectorService.calculateNorm(vector));
            Xpros.add(vectorService.calculateXProjection(vector));
            Ypros.add(vectorService.calculateYProjection(vector));
            logger.info("Added to lists");
        }
        JSONObject result = new JSONObject();
        result.put("minX1", params.stream().mapToDouble(map -> map.get("X1")).min().orElse(Double.MIN_VALUE));
        result.put("averageX1", params.stream().mapToDouble(map -> map.get("X1")).average().orElse(Double.MIN_VALUE));
        result.put("maxX1", params.stream().mapToDouble(map -> map.get("X1")).max().orElse(Double.MIN_VALUE));
        result.put("minY1", params.stream().mapToDouble(map -> map.get("Y1")).min().orElse(Double.MIN_VALUE));
        result.put("averageY1", params.stream().mapToDouble(map -> map.get("Y1")).average().orElse(Double.MIN_VALUE));
        result.put("maxY1", params.stream().mapToDouble(map -> map.get("Y1")).max().orElse(Double.MIN_VALUE));
        result.put("minX2", params.stream().mapToDouble(map -> map.get("X2")).min().orElse(Double.MIN_VALUE));
        result.put("averageX2", params.stream().mapToDouble(map -> map.get("X2")).average().orElse(Double.MIN_VALUE));
        result.put("maxX2", params.stream().mapToDouble(map -> map.get("X2")).max().orElse(Double.MIN_VALUE));
        result.put("minY2", params.stream().mapToDouble(map -> map.get("Y2")).min().orElse(Double.MIN_VALUE));
        result.put("averageY2", params.stream().mapToDouble(map -> map.get("Y2")).average().orElse(Double.MIN_VALUE));
        result.put("maxY2", params.stream().mapToDouble(map -> map.get("Y2")).max().orElse(Double.MIN_VALUE));logger.info("did half");Double averageNorm = norms.stream().mapToDouble(Double::doubleValue).sum() / norms.size();logger.info("1");result.put("minNorm", Collections.min(norms));result.put("averageNorm", averageNorm);
        result.put("maxNorm", Collections.max(norms));
        Double averageXPro = Xpros.stream().mapToDouble(Double::doubleValue).sum() / Xpros.size();
        logger.info("2");
        result.put("minXpro", Collections.min(Xpros));
        result.put("averageXpro", averageXPro);
        result.put("maxXpro", Collections.max(Xpros));
        Double averageYpro = Ypros.stream().mapToDouble(Double::doubleValue).sum() / Xpros.size();
        result.put("minYpro", Collections.min(Ypros));
        result.put("averageYpro", averageYpro);
        result.put("maxYpro", Collections.max(Ypros));

        logger.info("Output");
        return result.toString();
    }
}



//http://localhost:8080/vector?X1=10.0&Y1=0.0&X2=3.0&Y2=4.0