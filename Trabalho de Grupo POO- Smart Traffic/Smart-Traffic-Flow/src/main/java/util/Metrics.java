package util;

public class Metrics {

    private int carsServed = 0;
    private double totalWaitTime = 0;

    public void recordCar(double waitTime) {
        carsServed++;
        totalWaitTime += waitTime;
    }

    public int getCarsServed() {
        return carsServed;
    }

    public double getAverageWait() {
        return carsServed == 0 ? 0 : totalWaitTime / carsServed;
    }
}