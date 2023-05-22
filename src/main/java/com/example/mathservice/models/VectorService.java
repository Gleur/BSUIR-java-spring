package com.example.mathservice.models;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
    @Async
    public CompletableFuture<Double> calculateNormAsync(Vector vector) {
        // double norm = calculateNorm(vector);
        //  return CompletableFuture.completedFuture(norm);
        return CompletableFuture.supplyAsync(() -> calculateNorm(vector), CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS));
    }

    @Async
    public CompletableFuture<Double> calculateXProjectionAsync(Vector vector) {
   //     double xProjection = calculateXProjection(vector);
   //     return CompletableFuture.completedFuture(xProjection);
        return CompletableFuture.supplyAsync(() -> calculateXProjection(vector), CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS));
    }

    @Async
    public CompletableFuture<Double> calculateYProjectionAsync(Vector vector) {
     //   double yProjection = calculateYProjection(vector);
     //   return CompletableFuture.completedFuture(yProjection);
        return CompletableFuture.supplyAsync(() -> calculateYProjection(vector), CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS));
    }
}
