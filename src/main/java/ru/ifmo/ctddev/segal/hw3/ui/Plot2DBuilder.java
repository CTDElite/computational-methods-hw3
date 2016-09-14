package ru.ifmo.ctddev.segal.hw3.ui;

import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.util.List;

public class Plot2DBuilder {

    private Plot2DPanel plot;

    public Plot2DBuilder(String... axisLabels) {
        this.plot = new Plot2DPanel();
        this.plot.setAxisLabels(axisLabels);
    }

    public void addPlot(String plotName, List<Double> abscissas, List<Double> ordinates) {
        double[] primitiveAbscissas = abscissas.stream().mapToDouble(Double::doubleValue).toArray();
        double[] primitiveOrdinates = ordinates.stream().mapToDouble(Double::doubleValue).toArray();
        plot.addLinePlot(plotName, primitiveAbscissas, primitiveOrdinates);
    }

    public void show() {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}
