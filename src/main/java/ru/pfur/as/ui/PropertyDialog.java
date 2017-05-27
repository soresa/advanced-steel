package ru.pfur.as.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyDialog extends JDialog {

    public static Color back = Color.WHITE;
    public static int thickness = 3;
    public static Color lineColor = Color.BLACK;
    private final DrawPanel drawPanel;

    public PropertyDialog(Frame frame, String s, DrawPanel panel) {
        super(frame, s);
        this.drawPanel = panel;
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setSize(430, 180);
        this.setPreferredSize(new Dimension(430, 180));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new FlowLayout());

        initElement();
    }

    private void initElement() {

        this.add(getColorPanel());
        this.add(getColorLinePanel());
        this.add(getThicknessPanel());


    }

    private Component getColorLinePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(420, 40));
        final JLabel label = new JLabel("Color Line");
        label.setPreferredSize(new Dimension(150, 30));
        panel.add(label);
        final JTextField c = new JTextField();
        c.setPreferredSize(new Dimension(100, 30));
        c.setEditable(false);
        c.setBackground(lineColor);
        panel.add(c);
        JButton colorButton = new JButton("Choose color");
        setPreferredSize(colorButton);
        panel.add(colorButton);
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                lineColor = JColorChooser.showDialog(null, "Choose a color", lineColor);
                c.setBackground(lineColor);
                drawPanel.repaint();
            }
        });

        return panel;
    }

    private void setPreferredSize(JButton colorButton) {
        colorButton.setPreferredSize(new Dimension(140, 33));
    }

    private Component getThicknessPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(420, 40));

        final JLabel label = new JLabel("Line thickness");
        label.setPreferredSize(new Dimension(150, 30));
        panel.add(label);

        final JTextField c = new JTextField();
        c.setPreferredSize(new Dimension(100, 30));
        c.setEditable(true);
        c.setText(String.valueOf(thickness));

        panel.add(c);
        JButton colorButton = new JButton("Save thickness");

        setPreferredSize(colorButton);

        panel.add(colorButton);
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                thickness = Integer.parseInt(c.getText());
                drawPanel.repaint();
            }
        });

        return panel;
    }

    private Component getColorPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(420, 40));


        final JLabel label = new JLabel("Color background");
        label.setPreferredSize(new Dimension(150, 30));
        panel.add(label);

        final JTextField c = new JTextField();
        c.setPreferredSize(new Dimension(100, 30));
        c.setEditable(false);
        c.setBackground(back);

        panel.add(c);
        JButton colorButton = new JButton("Choose color");
        setPreferredSize(colorButton);
        panel.add(colorButton);
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                back = JColorChooser.showDialog(null, "Choose a color", back);
                c.setBackground(back);
                drawPanel.repaint();
            }
        });

        return panel;
    }
}
