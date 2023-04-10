package com.example.mathservice.models;

import org.springframework.stereotype.Service;

@Service
public class VectorService {

    public double calculateNorm(Vector vector)
    {
        double dx = vector.getX2() - vector.getX1();
        double dy = vector.getY2() - vector.getY1();
        return Math.sqrt(dx*dx+dy*dy);
    }

    public double calculateXProjection(Vector vector) {
        double dx = vector.getX2() - vector.getX1();
        return dx;
    }

    public double calculateYProjection(Vector vector) {
        double dy = vector.getY2() - vector.getY1();
        return dy;
    }
}
