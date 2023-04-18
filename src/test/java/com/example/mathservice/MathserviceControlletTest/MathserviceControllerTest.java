package com.example.mathservice.MathserviceControlletTest;

import static org.junit.jupiter.api.Assertions.*;

import com.example.mathservice.models.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.example.mathservice.models.Vector;
import com.example.mathservice.models.VectorService;

public class MathserviceControllerTest {

    @Test
    public void testCalculateNorm() {
        VectorService vectorService = new VectorService();
        Vector vector = new Vector(0, 0, 3, 4);
        double result = vectorService.calculateNorm(vector);
        assertEquals(5.0, result);
    }

    @Test
    public void testCalculateXProjection() {
        VectorService vectorService = new VectorService();
        Vector vector = new Vector(0, 0, 3, 4);
        double result = vectorService.calculateXProjection(vector);
        assertEquals(3.0, result);
    }

    @Test
    public void testCalculateYProjection() {
        VectorService vectorService = new VectorService();
        Vector vector = new Vector(0, 0, 3, 4);
        double result = vectorService.calculateYProjection(vector);
        assertEquals(4.0, result);
    }

    @Test
    public void testException() {
        Validator validator = new Validator();
        Vector vector = new Vector(110.0, 0, 3, 4);
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validate(vector.getX1());
        });
   }

}