package view;

import controller.Simulation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Intersection;
import model.Vehicle;
import model.EmergencyVehicle;
import model.TrafficLight;

public class CanvasView extends Canvas {

    private Simulation simulation;
    private GraphicsContext gc;

    public CanvasView(Simulation simulation) {
        super(800, 400);
        this.simulation = simulation;
        this.gc = getGraphicsContext2D();
    }

    public void render() {
        gc.clearRect(0, 0, getWidth(), getHeight());

        drawBackground();
        drawRoad();
        drawCrosswalks();
        drawTrafficLights();
        drawVehicles();
        drawStats();
    }

    // =========================
    // FUNDO
    // =========================
    private void drawBackground() {
        gc.setFill(Color.rgb(200, 220, 200));
        gc.fillRect(0, 170, 800, 230);
    }

    // =========================
    // ESTRADA
    // =========================
    private void drawRoad() {

        gc.setFill(Color.rgb(60, 120, 60));
        gc.fillRect(0, 0, 800, 400);

        gc.setFill(Color.rgb(150, 150, 150));
        gc.fillRect(360, 0, 80, 400);
        gc.fillRect(0, 160, 800, 80);

        gc.setFill(Color.WHITE);

        for (int y = 10; y < 400; y += 40) {
            gc.fillRect(398, y, 4, 20);
        }

        for (int x = 10; x < 800; x += 40) {
            gc.fillRect(x, 198, 20, 4);
        }
    }

    // =========================
    // PASSADEIRAS
    // =========================
    private void drawCrosswalks() {

        gc.setFill(Color.WHITE);

        for (int i = 0; i < 5; i++) {
            gc.fillRect(362 + i * 15, 150, 8, 10);
            gc.fillRect(362 + i * 15, 240, 8, 10);
            gc.fillRect(350, 172 + i * 15, 10, 8);
            gc.fillRect(440, 172 + i * 15, 10, 8);
        }
    }

    // =========================
    // VEÍCULOS
    // =========================
    private void drawVehicles() {

        long time = System.currentTimeMillis();
        boolean sirenOn = (time / 300) % 2 == 0;

        for (Vehicle v : simulation.getRoad().getVehicles()) {

            double x = v.getX();
            double y = v.getY();

            gc.setFill(Color.rgb(0, 0, 0, 0.25));
            gc.fillRoundRect(x + 3, y + 4, 28, 18, 8, 8);

            if (v instanceof EmergencyVehicle) {
                gc.setFill(Color.DARKRED);
            } else {
                gc.setFill(v.getColor());
            }

            gc.fillRoundRect(x, y, 28, 18, 8, 8);

            gc.setFill(Color.rgb(200, 230, 255));
            gc.fillRoundRect(x + 5, y + 3, 12, 6, 4, 4);

            if (v instanceof EmergencyVehicle) {
                gc.setFill(sirenOn ? Color.RED : Color.BLUE);
                gc.fillRect(x + 10, y - 4, 8, 4);
            }
        }
    }


    // =========================
    // SEMÁFOROS
    // =========================
    private void drawTrafficLights() {
        Intersection i = simulation.getIntersections().get(0);
        double cx = 400;
        double cy = 200;

        // Semáforos à direita de cada via
        drawSingleLight(cx - 70, cy - 10, i.getEwLight()); // Oeste
        drawSingleLight(cx + 50, cy - 130, i.getEwLight()); // Este
        drawSingleLight(cx - 67, cy - 130, i.getNsLight()); // Norte
        drawSingleLight(cx + 40, cy + 10, i.getNsLight()); // Sul
    }



    private void drawSingleLight(double x, double y, TrafficLight light) {

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(x + 12, y + 45, 6, 45);

        gc.setFill(Color.rgb(50, 50, 50));
        gc.fillRoundRect(x, y, 30, 45, 8, 8);

        Color color;
        switch (light.getStateName()) {
            case "GREEN" -> color = Color.LIMEGREEN;
            case "YELLOW" -> color = Color.GOLD;
            default -> color = Color.RED;
        }

        gc.setFill(color);
        gc.fillOval(x + 7, y + 8, 16, 16);
    }

    // =========================
    // ESTATÍSTICAS
    // =========================
    private void drawStats() {
        gc.setFill(Color.BLACK);
        gc.fillText(
                "Waiting cars: " + simulation.countWaitingCars(),
                10, 20
        );
    }
}
