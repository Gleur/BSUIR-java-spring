package com.example.mathservice.models;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vector {
    private double x1;
    private double x2;
    private double y1;
    private double y2;

    static Logger logger = LoggerFactory.getLogger(Vector.class);

    public Vector(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void validation() throws RuntimeException {
        //if(x1 > 100.0)
        if(!Double.isFinite(x1))
        {
            logger.info("The value of X1 is greater than double range ({})", x1);
            throw new IllegalArgumentException("The value of X1 is greater than double range");
        }
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }
}
