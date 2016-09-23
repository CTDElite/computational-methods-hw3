package ru.ifmo.ctddev.segal.hw3.ui;

import org.math.plot.Plot3DPanel;

import javax.swing.*;
import java.util.List;

public class Plot3DBuilder {

    private Plot3DPanel plot;

    public Plot3DBuilder() {
        this.plot = new Plot3DPanel("SOUTH");
    }

    public void addPlot(String plotName, List<Double> x, List<Double> y, List<Double> z) {
        double[] primitiveX = x.stream().mapToDouble(Double::doubleValue).toArray();
        double[] primitiveY = y.stream().mapToDouble(Double::doubleValue).toArray();
        double[] primitiveZ = z.stream().mapToDouble(Double::doubleValue).toArray();
        plot.addLinePlot(plotName, primitiveX, primitiveY, primitiveZ);
    }

    public void show() {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }

}
