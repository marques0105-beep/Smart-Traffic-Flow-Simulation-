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

        spawnVehicles(); // seguro: só executa uma vez

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

            // Semáforo
            for (Intersection i : intersections) {

                boolean approaching =
                        v.getPosition() >= i.getPosition() - 40 &&
                                v.getPosition() < i.getPosition();

                TrafficLight light =
                        (v.getAxis() == Axis.NORTH_SOUTH)
                                ? i.getNsLight()
                                : i.getEwLight();

                if (!light.isGreen() && approaching) {
                    mustStop = true;
                }
            }

            //  Colisão com veículo à frente
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

            double distance = other.getPosition() - current.getPosition();

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
                if (v.getPosition() >= i.getPosition() - 40 &&
                        v.getPosition() <= i.getPosition()) {
                    count++;
                }
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

        vehiclesSpawned = false; // 🔄 permite novo start
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
