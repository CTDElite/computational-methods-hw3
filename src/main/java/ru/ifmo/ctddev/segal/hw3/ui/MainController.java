package ru.ifmo.ctddev.segal.hw3.ui;


import ru.ifmo.ctddev.segal.hw3.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Random rand = new Random();

    @FXML
    private TextField sigmaValue;
    @FXML
    private TextField bValue;
    @FXML
    private TextField rValue;
    @FXML
    private TextField xValue;
    @FXML
    private TextField yValue;
    @FXML
    private TextField zValue;
    @FXML
    private TextField taValue;
    @FXML
    private TextField tbValue;
    @FXML
    private TextField dtValue;

    @FXML
    private CheckBox eulerMethod;
    @FXML
    private CheckBox implicitMethod;
    @FXML
    private CheckBox rungeMethod;
    @FXML
    private CheckBox adamsMethod;

    private double sigma;
    private double b;
    private double r;
    private double x0;
    private double y0;
    private double z0;
    private double ta;
    private double tb;
    private double dt;

    private List<MethodForLorentzSystem> methods = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sigmaValue.addEventFilter(KeyEvent.KEY_TYPED, new OnlyDouble());
        sigmaValue.setText("10");
        bValue.addEventFilter(KeyEvent.KEY_TYPED, new OnlyDouble());
        bValue.setText(Utils.doublePrecision(8. / 3));
        rValue.addEventFilter(KeyEvent.KEY_TYPED, new OnlyDouble());
        rValue.setText(String.valueOf(rand.nextInt(50)));

        xValue.addEventFilter(KeyEvent.KEY_TYPED, new OnlyDouble());
        xValue.setText(Utils.doublePrecision(rand.nextDouble()));
        yValue.addEventFilter(KeyEvent.KEY_TYPED, new OnlyDouble());
        yValue.setText(Utils.doublePrecision(rand.nextDouble()));
        zValue.addEventFilter(KeyEvent.KEY_TYPED, new OnlyDouble());
        zValue.setText(Utils.doublePrecision(rand.nextDouble()));

        taValue.addEventFilter(KeyEvent.KEY_TYPED, new OnlyDouble());
        taValue.setText("0");
        tbValue.addEventFilter(KeyEvent.KEY_TYPED, new OnlyDouble());
        tbValue.setText("10");
        dtValue.addEventFilter(KeyEvent.KEY_TYPED, new OnlyDouble());
        dtValue.setText("0.001");
    }

    private void update() {
        sigma = Double.parseDouble(sigmaValue.getText());
        r = Double.parseDouble(rValue.getText());
        b = Double.parseDouble(bValue.getText());

        x0 = Double.parseDouble(xValue.getText());
        y0 = Double.parseDouble(yValue.getText());
        z0 = Double.parseDouble(zValue.getText());

        ta = Double.parseDouble(taValue.getText());
        tb = Double.parseDouble(tbValue.getText());
        dt = Double.parseDouble(dtValue.getText());
    }

    private void build2DPlots(String firstAxis, String secondAxis) {
        if (!methods.isEmpty()) {
            Plot2DBuilder plot = new Plot2DBuilder(firstAxis, secondAxis);
            for (MethodForLorentzSystem method : methods) {
                Result result = method.run();
                plot.addPlot(method.getClass().getSimpleName(), result.t, result.x);
            }
            plot.show();
        }
    }

    @FXML
    private void build2DXTPlot(ActionEvent event) {
        build2DPlots("T", "X");
    }

    @FXML
    private void build2DYTPlot(ActionEvent event) {
        build2DPlots("T", "Y");
    }

    @FXML
    private void build2DZTPlot(ActionEvent event) {
        build2DPlots("T", "Z");
    }

    @FXML
    private void runClick(ActionEvent event) {

        methods.clear();
        update();
        if (eulerMethod.isSelected()) {
            methods.add(new EulerMethod(sigma, b, r, ta, tb, dt, x0, y0, z0));
        }
        if (implicitMethod.isSelected()) {
            methods.add(new ImplicitEulerMethod(x0, y0, z0, sigma, b, r, ta, tb, dt));
        }
        if (rungeMethod.isSelected()) {
            methods.add(new RungeKuttaMethod(x0, y0, z0, sigma, b, r, ta, tb, dt));
        }
        if (adamsMethod.isSelected()) {
            methods.add(new AdamsBashforthMoultonMethod(x0, y0, z0, sigma, b, r, ta, tb, dt));
        }

        Thread[] threads = new Thread[methods.size()];
        for (int i = 0; i < methods.size(); i++) {
            final int tmp = i;
            threads[tmp] = new Thread(() -> methods.get(tmp).run());
        }

        for (Thread thread : threads) {
            thread.run();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class OnlyDouble implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if (!event.getCharacter().matches("[0-9. \n-]")) {
                event.consume();
            }
        }
    }
}