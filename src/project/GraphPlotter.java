package project;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;

import static project.InitializerUI.getRoot;

/**
 * <b>
 * Author: Dmytro Pelovych.
 * <p>
 * File: GraphPlotter.java.
 * <p>
 * Dedication: after GraphRoot.java have collected data needed to plot a graph,
 * this class takes its part to draw a graph apart the main frame.
 * </b>
 */
public class GraphPlotter extends JFrame {
    private static ArrayList<Double> x_list;
    private static ArrayList<Double> y_list;
    private static final String TITLE = "X = ρ∙cos(φ), Y = ρ∙sin(φ), ρ = R + A∙cos(2πφ/S)";

    public GraphPlotter() {
        initiliazeGP();
    }

    /**
     * <b>The method setups all Swing components to their places, adjust button action listeners etc.</b>
     */
    private void initiliazeGP() {
        setTitle("Graph Plotter | Made by D. Pelovych ©");
        setIconImage(new ImageIcon("icon.png").getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(InitializerUI.getFrame());
        setSize(1000, 800);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                InitializerUI.resetWindow();
            }
        });

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        chartPanel.setDomainZoomable(true);
        chartPanel.setMouseWheelEnabled(true);

        add(chartPanel);
        pack();
        revalidate();
    }

    /**
     * <b>The method creates a chart using prepared dataset. It add markers, grid, scale etc.</b>
     *
     * @return chart
     */
    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                null, "Abscissa (X)", "Ordinate (Y)",
                dataset, PlotOrientation.VERTICAL,
                true, true, false);

        ValueMarker marker = new ValueMarker(0.0);
        XYPlot plot = chart.getXYPlot();
        marker.setPaint(Color.BLUE);
        plot.addDomainMarker(marker);
        plot.addRangeMarker(marker);

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);

        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.setTitle(new TextTitle(TITLE, new Font("Serif", java.awt.Font.BOLD, 18)));

        return chart;
    }

    /**
     * <b>The method creates a dataset by extracting correspondent co-ordinates from collections.</b>
     *
     * @return dataset consisting of X/Y co-ordinates
     */
    private XYDataset createDataset() {
        GraphRoot root = getRoot();
        StringBuilder builder = new StringBuilder();
        builder.append("R = ").append(root.getParameters()[0])
                .append(", A = ").append(root.getParameters()[1])
                .append(", S = ").append(root.getParameters()[2])
                .append(", φ (min) = ").append(root.getParameters()[3])
                .append(", φ (max) = ").append(root.getParameters()[4])
                .append(", Δφ = ").append(root.getParameters()[5])
                .append("\nCredits to D. Pelovych ©");
        var dataset = new XYSeriesCollection();
        var series = new XYSeries(builder.toString());
        for (int index = 0; index < x_list.size(); index++)
            series.add(x_list.get(index), y_list.get(index));
        dataset.addSeries(series);

        return dataset;
    }

    /**
     * <b>The method receives data needed to plot a graph.</b>
     */
    public static void handleData(GraphRoot root) {
        x_list = root.getX_list();
        y_list = root.getY_list();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new GraphPlotter().setVisible(true));
    }
}