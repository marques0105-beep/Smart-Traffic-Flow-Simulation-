package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;


public class ControlPanel extends HBox {

    private Button startBtn;
    private Button stopBtn;
    private Button resetBtn;
    private Slider speedSlider;
    private Button exportBtn;


    public ControlPanel(Runnable onStart, Runnable onStop, Runnable onReset,
                        Runnable onExport, Slider slider)
    {


        setSpacing(10);
        setPadding(new Insets(10));

        startBtn = new Button("Start");
        stopBtn = new Button("Stop");
        resetBtn = new Button("Reset");
        exportBtn = new Button("Export Csv");



        startBtn.setOnAction(e -> onStart.run());
        stopBtn.setOnAction(e -> onStop.run());
        resetBtn.setOnAction(e -> onReset.run());
        exportBtn.setOnAction(e -> onExport.run());

        speedSlider = slider; // Slider passado do MainApp

        Label speedLabel = new Label("Simulation speed");

        HBox buttons = new HBox(10, startBtn, stopBtn, resetBtn, exportBtn);
        getChildren().addAll(buttons, speedLabel, speedSlider);
    }
}
