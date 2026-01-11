package util;

import controller.Simulation;

import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {

    public static void export(Simulation simulation, String fileName) {

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.append("TotalVehicles,WaitingVehicles\n");

            int totalVehicles = simulation.getRoad().getVehicles().size();
            int waitingVehicles = simulation.countWaitingCars();

            writer.append(totalVehicles + "," + waitingVehicles + "\n");

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
