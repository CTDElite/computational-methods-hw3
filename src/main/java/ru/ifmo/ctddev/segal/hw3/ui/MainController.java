package ru.ifmo.ctddev.segal.hw3.ui;


import javafx.application.Platform;
import ru.ifmo.ctddev.segal.hw3.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import ru.ifmo.ctddev.segal.hw3.predictor_corrector_methods.AdamsBashforthMoultonMethod;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.function.Function;

public class MainController implements Initializable {

    private Random rand = new Random();

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

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

    private List<MethodForLorenzSystem> methods = new ArrayList<>();

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
        methods.clear();

        sigma = Double.parseDouble(sigmaValue.getText());
        r = Double.parseDouble(rValue.getText());
        b = Double.parseDouble(bValue.getText());

        x0 = Double.parseDouble(xValue.getText());
        y0 = Double.parseDouble(yValue.getText());
        z0 = Double.parseDouble(zValue.getText());

        ta = Double.parseDouble(taValue.getText());
        tb = Double.parseDouble(tbValue.getText());
        dt = Double.parseDouble(dtValue.getText());

        if (eulerMethod.isSelected()) {
            methods.add(new EulerMethod(sigma, b, r, ta, tb, dt, x0, y0, z0));
        }
        if (implicitMethod.isSelected()) {
            methods.add(new ImplicitEulerMethod(sigma, b, r, ta, tb, dt, x0, y0, z0));
        }
        if (rungeMethod.isSelected()) {
            methods.add(new RungeKuttaMethod(sigma, b, r, ta, tb, dt, x0, y0, z0));
        }
        if (adamsMethod.isSelected()) {
            methods.add(new AdamsBashforthMoultonMethod(sigma, b, r, ta, tb, dt, x0, y0, z0, AdamsBashforthMoultonMethod.Steps.FOUR));
        }
    }

    private void build2DPlots(String firstAxis,
                                       String secondAxis,
                                       Function<Result, List<Double>> abscissas,
                                       Function<Result, List<Double>> ordinates) {
        executorService.submit(() -> {
            if (!methods.isEmpty()) {
                Plot2DBuilder plot = new Plot2DBuilder(firstAxis, secondAxis);
                for (MethodForLorenzSystem method : methods) {
                    Result result = method.call();
                    plot.addPlot(method.getClass().getSimpleName(), abscissas.apply(result), ordinates.apply(result));
                }
                Platform.runLater(plot::show);
            }
        });
    }

    @FXML
    private void build2DXTPlot(ActionEvent event) {
        update();
        build2DPlots("T", "X", result -> result.t, result -> result.x);
    }

    @FXML
    private void build2DYTPlot(ActionEvent event) {
        update();
        build2DPlots("T", "Y", result -> result.t, result -> result.y);
    }

    @FXML
    private void build2DZTPlot(ActionEvent event) {
        update();
        build2DPlots("T", "Z", result -> result.t, result -> result.z);
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
