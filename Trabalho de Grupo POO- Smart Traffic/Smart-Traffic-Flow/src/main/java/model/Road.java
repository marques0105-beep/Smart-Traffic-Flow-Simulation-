package model;

import java.util.ArrayList;
import java.util.List;

public class Road {

    private double length;
    private List<Vehicle> vehicles = new ArrayList<>();

    public Road(double length) {
        this.length = length;
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void update(double dt) {
        for (Vehicle v : vehicles) {
            v.update(dt);
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }


    public double getLength() {
        return length;
    }
}