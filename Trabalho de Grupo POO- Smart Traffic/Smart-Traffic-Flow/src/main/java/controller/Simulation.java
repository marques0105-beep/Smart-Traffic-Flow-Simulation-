package controller;

import model.*;
import util.Metrics;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private Road road;
    private List<Intersection> intersections;
    private AdaptiveCycle adaptiveStrategy;
    private Metrics metrics;

    private boolean vehiclesSpawned = false;

    public Simulation() {

        adaptiveStrategy = new AdaptiveCycle();
        metrics = new Metrics();

        road = new Road(800);
        intersections = new ArrayList<>();

        intersections.add(new Intersection(adaptiveStrategy, 300));
    }

    // ============================
    //  SPAWN
    // ============================
    public void spawnVehicles() {

        if (vehiclesSpawned) return;
        vehiclesSpawned = true;

        road.addVehicle(new Vehicle(750, Axis.NORTH_SOUTH));
        road.addVehicle(new Vehicle(100, Axis.SOUTH_NORTH));
        road.addVehicle(new Vehicle(0, Axis.EAST_WEST));
        road.addVehicle(new Vehicle(800, Axis.WEST_EAST));

        road.addVehicle(new EmergencyVehicle(50, Axis.NORTH_SOUTH));
        road.addVehicle(new EmergencyVehicle(750, Axis.EAST_WEST));
    }

    // ============================
    //  UPDATE
    // ============================
    public void update(double dt) {

        spawnVehicles();

        adaptiveStrategy.setWaitingCars(countWaitingCars());

        for (Intersection i : intersections) {
            i.update(dt);
        }

        for (Vehicle v : road.getVehicles()) {

            if (v instanceof EmergencyVehicle) {
                v.go();
                continue;
            }

            boolean mustStop = false;

            // ============================
            //  SEMÁFORO
            // ============================
            for (Intersection i : intersections) {

             
                boolean approaching = switch (v.getAxis()) {
                    case EAST_WEST, NORTH_SOUTH ->
                            v.getPosition() >= i.getPosition() - 40 &&
                                    v.getPosition() < i.getPosition();

                    case WEST_EAST, SOUTH_NORTH ->
                            v.getPosition() <= i.getPosition() + 40 &&
                                    v.getPosition() > i.getPosition();
                };

                TrafficLight light =
                        (v.getAxis() == Axis.NORTH_SOUTH || v.getAxis() == Axis.SOUTH_NORTH)
                                ? i.getNsLight()
                                : i.getEwLight();

                if (!light.isGreen() && approaching) {
                    mustStop = true;
                }
            }

            // ============================
            //  COLISÃO COM VEÍCULO À FRENTE
            // ============================
            if (vehicleAhead(v)) {
                mustStop = true;
            }

            if (mustStop) v.stop();
            else v.go();
        }

        road.update(dt);
    }

    // ============================
    //  COLISÃO
    // ============================
    private boolean vehicleAhead(Vehicle current) {

        for (Vehicle other : road.getVehicles()) {

            if (other == current) continue;
            if (other instanceof EmergencyVehicle) continue;
            if (other.getAxis() != current.getAxis()) continue;


            double distance = switch (current.getAxis()) {
                case EAST_WEST, NORTH_SOUTH ->
                        other.getPosition() - current.getPosition();
                case WEST_EAST, SOUTH_NORTH ->
                        current.getPosition() - other.getPosition();
            };

            if (distance > 0 && distance < 30) {
                return true;
            }
        }
        return false;
    }

    // ============================
    //  CONTAR ESPERAS
    // ============================
    public int countWaitingCars() {

        int count = 0;

        for (Vehicle v : road.getVehicles()) {
            for (Intersection i : intersections) {

                // mesma lógica de aproximação
                boolean waiting = switch (v.getAxis()) {
                    case EAST_WEST, NORTH_SOUTH ->
                            v.getPosition() >= i.getPosition() - 40 &&
                                    v.getPosition() <= i.getPosition();

                    case WEST_EAST, SOUTH_NORTH ->
                            v.getPosition() <= i.getPosition() + 40 &&
                                    v.getPosition() >= i.getPosition();
                };

                if (waiting) count++;
            }
        }
        return count;
    }

    // ============================
    //  RESET
    // ============================
    public void reset() {

        road = new Road(800);
        intersections.clear();
        intersections.add(new Intersection(adaptiveStrategy, 300));

        vehiclesSpawned = false;
    }

    // ============================
    //  GETTERS
    // ============================
    public Road getRoad() {
        return road;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}
