package project;

import java.awt.*;
import javax.swing.*;

/**
 * <b>
 * Author: Dmytro Pelovych.
 * <p>
 * File: InitializerUI.java.
 * <p>
 * Dedication: this class implements UI where a user can input parameters required and get a graph plotted.
 * </b>
 */
public class InitializerUI {
    private static JFrame frame;
    private static JTextField R_field;
    private static JTextField A_field;
    private static JTextField S_field;
    private static JTextField phi_min_field;
    private static JTextField phi_max_field;
    private static JTextField step_field;
    private static JButton plotGraph_button;
    private static JButton clearFields_button;
    private static GraphRoot root;

    /**
     * <b>
     * The method setups all Swing components to their places, adjust button action listeners etc.
     * <p>
     * Note: action listeners contains VFC (Values Format Check).
     * </b>
     */
    public static void initializeUI() { JLabel R_label = new JLabel("Parameter R");
        JLabel A_label = new JLabel("Parameter A");
        JLabel S_label = new JLabel("Parameter S");
        JLabel phi_min_label = new JLabel("Parameter φ (min)");
        JLabel phi_max_label = new JLabel("Parameter φ (max)");
        JLabel step_label = new JLabel("Step Δφ");
        JLabel label = new JLabel("     X = ρ∙cos(φ), Y = ρ∙sin(φ), ρ = R + A∙cos(2πφ/S)");
        label.setFont(new Font("Serif", Font.BOLD, 18));
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        label.setHorizontalTextPosition(SwingConstants.CENTER);

        JPanel R_panel = new JPanel();
        JPanel A_panel = new JPanel();
        JPanel S_panel = new JPanel();
        JPanel phi_min_panel = new JPanel();
        JPanel phi_max_panel = new JPanel();
        JPanel step_panel = new JPanel();
        JPanel left_panel = new JPanel();
        JPanel right_panel = new JPanel();
        JPanel main_panel = new JPanel();

        R_field = new JTextField(5);
        A_field = new JTextField(5);
        S_field = new JTextField(5);
        phi_min_field = new JTextField(5);
        phi_max_field = new JTextField(5);
        step_field = new JTextField(5);
        clearFields_button = new JButton("Clear Fields");
        plotGraph_button = new JButton("Plot Graph");

        frame = new JFrame("Graph Plotter | Made by D. Pelovych ©");
        frame.setIconImage(new ImageIcon("icon.png").getImage());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(450, 225);
        frame.setResizable(false);
        frame.setVisible(true);

        S_field.setToolTipText("Input value in degrees (0-360º)");
        phi_min_field.setToolTipText("Input value in degrees (0-360º)");
        phi_max_field.setToolTipText("Input value in degrees (0-360º)");

        R_panel.add(R_label);
        R_panel.add(R_field);
        A_panel.add(A_label);
        A_panel.add(A_field);
        S_panel.add(S_label);
        S_panel.add(S_field);
        phi_min_panel.add(phi_min_label);
        phi_min_panel.add(phi_min_field);
        phi_max_panel.add(phi_max_label);
        phi_max_panel.add(phi_max_field);
        step_panel.add(step_label);
        step_panel.add(step_field);

        left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
        main_panel.setLayout(new BorderLayout());
        main_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        left_panel.add(R_panel);
        left_panel.add(A_panel);
        left_panel.add(S_panel);
        left_panel.add(plotGraph_button);
        right_panel.add(phi_min_panel);
        right_panel.add(phi_max_panel);
        right_panel.add(step_panel);
        right_panel.add(clearFields_button);
        main_panel.add(left_panel, BorderLayout.WEST);
        main_panel.add(right_panel, BorderLayout.EAST);
        frame.add(label, BorderLayout.NORTH);
        frame.add(main_panel, BorderLayout.CENTER);

        clearFields_button.addActionListener(event -> clearFields());
        plotGraph_button.addActionListener(event -> {
            try {
                String R_string = R_field.getText();
                String A_string = A_field.getText();
                String S_string = S_field.getText();
                String phi_min_string = phi_min_field.getText();
                String phi_max_string = phi_max_field.getText();
                String step_string = step_field.getText();

                if (R_string.isEmpty() || A_string.isEmpty() || S_string.isEmpty()
                        || phi_min_string.isEmpty() || phi_max_string.isEmpty()
                        || step_string.isEmpty()) throw new Throwable("Input required! Try again!");

                double R = Double.parseDouble(R_string);
                double A = Double.parseDouble(A_string);
                double S = Double.parseDouble(S_string);
                double phi_min = Double.parseDouble(phi_min_string);
                double phi_max = Double.parseDouble(phi_max_string);
                double step = Double.parseDouble(step_string);

                if (R <= 0 || A <= 0 || S < 0 || S >= 360 || phi_min < 0 || phi_min > 360
                        || phi_max < 0 || phi_max > 360 || phi_min >= phi_max) {
                    clearFields();
                    throw new Throwable("Illegal bounds! Try again!");
                }

                plotGraph_button.setEnabled(false);
                clearFields_button.setEnabled(false);
                R_field.setEditable(false);
                A_field.setEditable(false);
                S_field.setEditable(false);
                phi_min_field.setEditable(false);
                phi_max_field.setEditable(false);
                step_field.setEditable(false);

                root = new GraphRoot(R, A, S, phi_min, phi_max, step);
                GraphPlotter.handleData(root);
                GraphPlotter.main(null);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(frame, "Error occurred while parsing values!",
                        "Error Message", JOptionPane.ERROR_MESSAGE);
                clearFields();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(frame, "Error occurred! Try again!",
                        "Error Message", JOptionPane.ERROR_MESSAGE);
                clearFields();
            } catch (Throwable throwable) {
                JOptionPane.showMessageDialog(frame, throwable.getMessage(),
                        "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * <b>The method clears fields if called.</b>
     */
    private static void clearFields() {
        R_field.setText("");
        A_field.setText("");
        S_field.setText("");
        phi_min_field.setText("");
        phi_max_field.setText("");
        step_field.setText("");
    }

    /**
     * <b>
     * The method is called to reset the main frame right after the moment GraphPlotter.java frame is closed.
     * </b>
     */
    public static void resetWindow() {
        clearFields();
        plotGraph_button.setEnabled(true);
        clearFields_button.setEnabled(true);
        R_field.setEditable(true);
        A_field.setEditable(true);
        S_field.setEditable(true);
        phi_min_field.setEditable(true);
        phi_max_field.setEditable(true);
        step_field.setEditable(true);
    }

    /**
     * <b>The method gives an access to the main frame.</b>
     *
     * @return previously initialized frame
     */
    public static JFrame getFrame() {
        return frame;
    }

    /**
     * <b>The method gives an access to root created.</b>
     *
     * @return root
     */
    public static GraphRoot getRoot() {
        return root;
    }

    public static void main(String[] args) {
        initializeUI();
    }
}
