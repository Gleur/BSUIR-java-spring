package com.example.mathservice.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {

    static Logger logger = LoggerFactory.getLogger(Vector.class);

    public void validate(double input) throws RuntimeException {
        if(input > 100.0)
        {
            logger.error("The value of X1 is greater than 1oo ({})", input);
            throw new IllegalArgumentException("The value is greater than 100");
        }
    }
}
