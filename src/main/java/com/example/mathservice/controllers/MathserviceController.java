package com.example.mathservice.controllers;

import com.example.mathservice.models.Vector;
import com.example.mathservice.models.VectorService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathserviceController {

    Logger logger = LoggerFactory.getLogger(MathserviceController.class);

    @GetMapping("/vector")
    public String VectorService(@RequestParam("X1") double x1,
                                @RequestParam("Y1") double y1,
                                @RequestParam("X2") double x2,
                                @RequestParam("Y2") double y2) throws IllegalArgumentException, JSONException {

        logger.info("Started successfully");
        logger.info("Received request with parameters: x1={}, y1={}, x2={}, y2={}", x1, y1, x2, y2);

        Vector vector = new Vector(x1, y1, x2, y2);
        logger.info("x1 validation");
        vector.validation();
        VectorService vectorService = new VectorService();
        logger.info("VectorService initialization");
        JSONObject result = new JSONObject();
        result.put("VectorNorm: ", vectorService.calculateNorm(vector));
        result.put("X projection: ", vectorService.calculateXProjection(vector));
        result.put("Y projection: ", vectorService.calculateYProjection(vector));

        return result.toString();
    }

}
