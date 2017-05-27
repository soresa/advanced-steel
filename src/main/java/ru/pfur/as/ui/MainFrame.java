package ru.pfur.as.ui;

import lombok.extern.slf4j.Slf4j;
import ru.pfur.as.util.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

@Slf4j
public class MainFrame extends JFrame implements DrawInterface {
    private static final int E = 29000;
    JTextField valueFy = null; //Imported from swing
    JTextField valueL = null;
    JTextField valueW = null;
    JTextField valueTF = null;


    JLabel valueH = new JLabel("");
    JLabel valueHw = new JLabel("");
    JLabel valueTW = new JLabel("");
    JLabel selectedSteelValue = new JLabel("");

    PropertyDialog propertyDialog = null;


    // for calculate

    double finalBf;
    double finalTf;
    double trytw;
    JFrame frame;
    private double HW;
    private double H;
    private double fy;
    private double l;
    private double w;
    private double TF;
    private double htw;
    private double formula;
    private double Muadj;
    private JButton calculate;
    private JTextField valueFinalBF;
    private JTextField valueFinalTF;
    private JButton draw;

    private DrawPanel drawPanel;
    private boolean isDraw = false;
    private double C;


    public MainFrame(String title) {    // constructor
        super(title);

        init();
        frame = this;
//        setSize(550, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
//        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(getLeftPanel(), BorderLayout.WEST);
        drawPanel = getRightPanel();
        add(drawPanel, BorderLayout.CENTER);
        propertyDialog = new PropertyDialog(this, "Parameters", drawPanel);
//        setResizable(false);
    }


    private void init() {
    }

    private DrawPanel getRightPanel() {
        DrawPanel right = new DrawPanel(this);
        right.setLayout(new BorderLayout());
        right.setBackground(Color.WHITE);
        return right;
    }

    private JPanel getLeftPanel() {
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(550, 580)); //This line has no effect on z interface?
//        left.setLayout(new FlowLayout(0, 2, 2));
        left.setLayout(new BorderLayout(2, 2));

        JPanel center = new JPanel(new FlowLayout(0, 2, 2));
        center.add(getInputPanel());
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setPreferredSize(new Dimension(2, 550));
        center.add(label);
        center.add(getOutputPanel());
        left.add(center, BorderLayout.CENTER);


        JButton preCalculateButton = new JButton("Pre calculation");
        preCalculateButton.setPreferredSize(new Dimension(150, 36));
        preCalculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                valueFinalBF.setEnabled(false);
                valueFinalTF.setEnabled(false);

                valueFinalBF.setText("");
                valueFinalTF.setText("");

                calculate.setEnabled(false);
                draw.setEnabled(false);
                preCalculation();
            }
        });


        calculate = new JButton("Finish calculation");
        calculate.setEnabled(false);
        calculate.setPreferredSize(new Dimension(140, 36));
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                secondCalculate();
            }
        });


        draw = new JButton("Draw");
        draw.setEnabled(true);
        draw.setPreferredSize(new Dimension(60, 36));
        draw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                draw();
            }
        });


        JPanel buttons = new JPanel(new FlowLayout(0, 2, 2));

        buttons.add(preCalculateButton);
        buttons.add(calculate);
        buttons.add(draw);
        buttons.add(getPropertyButton());

        left.add(buttons, BorderLayout.SOUTH);

        return left;
    }

    private Component getPropertyButton() {
        JButton property = new JButton("Property");
        property.setPreferredSize(new Dimension(80, 36));
        property.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                propertyDialog.setVisible(true);
            }
        });
        return property;
    }

    private void draw() {
        isDraw = true;
        drawPanel.repaint();
    }

    public void preCalculation() {
        try {
            fy = getValue(valueFy, "Fy");
            l = getValue(valueL, "L");
            w = getValue(valueW, "w");
            TF = getValue(valueTF, "Tf");

            H = MathUtils.calculateH(l);

            valueH.setText("" + H);

            HW = 0;

            formula = 5.7 * Math.sqrt(E / fy); //5.7*SQRT(29000/B5)
            log.debug("5.7√(E/Fy) = " + formula);

            HW = H - 2 * TF;
            log.debug("hw = " + HW);

            double param2 = HW / formula; //tw<=h/(5.7...)
            log.debug("tw<=h/(5.7...) = " + param2);

            double param3 = 12 * Math.sqrt(E / fy);//h/tw = 12*SQRT(29000/B5)

            double param4 = HW / param3; // a/h<=1.6
            log.debug("tw>=h/(12.0...) = " + param4);

            double param5 = 0.4 * E / fy; //=0.4*29000/B5

            double param41 = HW / param5; // a/h>1.6
            log.debug("tw>=h/(0.40…) = " + param41);

            double select = Math.max(param4, param41);

            String tryTW =
                    JOptionPane.showInputDialog(frame, "Please input tw between " + String.format("%.4f", param2) + " and " + String.format("%.4f", select), "Input tw", JOptionPane.INFORMATION_MESSAGE);

            log.debug("tryTW = " + tryTW);

            if (tryTW != null && !tryTW.isEmpty()) {
                if (isEquation(tryTW)) {
                    String[] values = tryTW.split("/");

                    if (values.length != 2)
                        JOptionPane.showMessageDialog(frame, "Error Equation ");
                    trytw = Double.parseDouble(values[0]) / Double.parseDouble(values[1]);
                } else {

                    trytw = Double.parseDouble(tryTW);
                    if (trytw < select || trytw > param2) {
                        JOptionPane.showMessageDialog(frame, "Value must be between " + String.format("%.4f", param2) + " and " + String.format("%.4f", select));
                        return;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Input value must be between " + String.format("%.4f", param2) + " and " + String.format("%.4f", select));
                return;
            }

            htw = HW / trytw;
            log.debug("h/tw = " + htw);

            double Mu = 1.2 * w * l * l / 8;    //=(H5*(D5^2))/8

            log.debug("Mu = " + Mu);

            double Mu09fh = Mu * 12 / (0.9 * fy * HW);
            log.debug("Mu/(0.9fh) = " + Mu09fh);

            double Aw6 = trytw * HW / 6;
            log.debug("Aw/6 = " + Aw6);

            double Afsingle = Mu09fh - Aw6;  //Af(single)(in2)
            log.debug("Afsingle (in2) = " + Afsingle);

            double bf = Afsingle / TF;  //bf = Af/tf
            log.debug("bf (in) = " + bf);

            double tryflange = Afsingle / TF;

            String tryFlange =
                    JOptionPane.showInputDialog(frame, "Please input bf not less than " + String.format("%.4f", bf), "Input bf", JOptionPane.INFORMATION_MESSAGE);

            log.debug("tryFlange, bf (in) = " + tryFlange);

            if (tryFlange != null && !tryFlange.isEmpty()) {


                tryflange = Double.parseDouble(tryFlange);
                if (tryflange < bf) {
                    JOptionPane.showMessageDialog(frame, "Input value can't be less than " + String.format("%.4f", bf));

                    //continue from here 15Apr, 17

                    return;
                }

            } else {
                JOptionPane.showMessageDialog(frame, "Input value must be between " + String.format("%.4f", param2) + " and " + String.format("%.4f", select));
                return;
            }
            double Aftotal = 2 * tryflange * TF;
            log.debug("Aftotal (in2) = " + Aftotal);

            double Aw = trytw * HW;
            log.debug("Aw = " + Aw);

            double Atotal = Aftotal + Aw;
            log.debug("Atotal (in2) = " + Atotal);

            double TryWeight = (Atotal * 490 / 144) / 1000;
            log.debug("Weight (kips/ft) = " + TryWeight);

            String Weight =
                    JOptionPane.showInputDialog(frame, "Please input weight(kips/ft) not less than " + String.format("%.4f", TryWeight), "Input Weight(kips/ft)", JOptionPane.INFORMATION_MESSAGE);

            log.debug("Weight(kips/ft) = " + Weight);

            double inputTryWeight;
            if (Weight != null && !Weight.isEmpty()) {

                inputTryWeight = Double.parseDouble(String.valueOf(Weight));
                if (TryWeight > inputTryWeight) {
                    JOptionPane.showMessageDialog(frame, "Input value can't be less than " + String.format("%.4f", TryWeight));

                    return;
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Input value can't be empty! ");
                return;
            }

            log.debug("final Weight = " + inputTryWeight);


            Muadj = Mu + 1.2 * inputTryWeight * l * l / 8;
            log.debug("Muadj (ft-kips) = " + Muadj);

            double Mu09fh2 = Muadj * 12 / (0.9 * fy * HW);
            log.debug("Muadj (in2) = " + Mu09fh2);

            double Aw62 = trytw * HW / 6;
            log.debug("Aw/6 = " + Aw62);

            double AfMod = Mu09fh2 - Aw62;
            log.debug("Afsingle2 (in2) = " + AfMod);

            double bfAdj = AfMod / TF;
            log.debug("bfAdj (in) = " + bfAdj);

            if (tryflange >= bfAdj)
                finalBf = tryflange;
            else {
                String inputFinalBf =
                        JOptionPane.showInputDialog(frame, "Please input Bf greater than " + String.format("%.4f", bfAdj), "Input Bf(in)", JOptionPane.INFORMATION_MESSAGE);

                log.debug("Bf (in) = " + inputFinalBf);


                if (inputFinalBf != null && !inputFinalBf.isEmpty()) {

                    finalBf = Double.parseDouble(String.valueOf(inputFinalBf));
                    if (bfAdj > finalBf) {
                        JOptionPane.showMessageDialog(frame, "Input value can't be less than " + String.format("%.4f", bfAdj));
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Input value can't be empty! ");
                    return;
                }
            }

            finalTf = TF;

            log.debug("Final Bf = " + finalBf);
            log.debug("Final Tf = " + finalTf);

            valueFinalBF.setText(String.valueOf(finalBf));
            valueFinalBF.setEnabled(true);
            valueFinalTF.setText(String.valueOf(finalTf));
            valueFinalTF.setEnabled(true);

            calculate.setEnabled(true);
            JOptionPane.showMessageDialog(frame, "Pre calculation is successful.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());

        }
    }


    public void secondCalculate() {

//                    display z final selection (tf & bf)


        finalBf = Double.parseDouble(valueFinalBF.getText());
        finalTf = Double.parseDouble(valueFinalTF.getText());

        C = H / 2 - finalTf / 2;         //param3 = 12 * Math.sqrt(E / fy);//h/tw = 12*SQRT(29000/B5)
        log.debug("C (in) = " + C);         //double param5 = 0.4 * E / fy; //=0.4*29000/B5


        double Iw = trytw * HW * HW * HW / 12;    //bh3/12
        log.debug("Iw (in^4) = " + Iw);

        double If = 2 * finalTf * finalBf * C * C;     //2tbc^2
        log.debug("If (in^4) = " + If);

        double Ix = If + Iw;                //2tbc^2
        log.debug("Ix (in^4) = " + Ix);

        double Sxc = Ix / (H / 2);            //Sxc = Ix/c
        log.debug("Sxc (in^3) = " + Sxc);

        double Af = finalTf * finalBf;           //Af (trial section) = tf*bf
        log.debug("Af (trial section) (in^2) = " + Af);

        //Step 4: Compression Flange Strength
        //The critical compression flange stress Fcr depends on whether the flange is
        //compact, noncompact, or slender. The AISC Specification uses the generic notation
        //l, lp, and lr to define the flange width-to-thickness ratio and its limits.
        //From AISC Table B4.1b, The bending strength reduction factor is given by

        double aw = HW * trytw / (finalBf * finalTf);  //bfc = bfc = width of the compression flange
        //TF = tfc = thickness of the compression flange
        //hc = h for girders with equal flanges). hc = twice the distance from the elastic neutral axis
        //(the centroidal axis) to the inside face of the compression flange.
        //(hc/2 defines the part of the web thatis in compression for elastic bending.

        if (aw > 10) {
            aw = 10;                // Check this from the code
        }

        log.debug("aw = " + aw);

        double Rpg = 1 - (aw / (1200 + 300 * aw)) * (htw - formula);


        if (Rpg > 1)
            Rpg = 1;                // Check this from the code
        log.debug("Rpg = " + Rpg);

        double λ = finalBf / (2 * finalTf);
        log.debug("λ (bf/2tf) = " + λ);

        double λp = 0.38 * Math.sqrt(E / fy);
        log.debug("λp (0.38√(E/Fy)) = " + λp);

        double Kc = 4 / Math.sqrt(htw);

        if (Kc < 0.35 || Kc > 0.76) {
            Kc = (0.35 + 0.76) / 2;        // Check this from the code
        }

        log.debug("Kc = " + Kc);

        double Fl = 0.7 * fy;             //for girders with slender webs. (See AISC Table B4.1b for compact and noncompact webs.)

        log.debug("Fl = " + Fl);

        double λr = 0.95 * Math.sqrt(Kc * E / Fl);
        log.debug("λ (bf/2tf) = " + λ);

        double Fcr = 0;
        if (λ <= λp) {                             //the flange is compact. The limit state of yielding will control, and Fcr = Fy, resulting in: Mn = Rpg Fy Sxc
            Fcr = fy;

        }

        if (λp < λ && λ <= λr) {                     //the flange is noncompact. Inelastic FLB will control, and Fcr = fy - 0.3...
            Fcr = fy - 0.3 * fy * (λ - λp) / (λr - λp);

        }

        if (λ > λr) {                            //the flange is slender, elastic FLB will control, and
            Fcr = 0.9 * E * Kc / Math.pow(2, (finalBf / (2 * finalTf)));
        }

        if (Fcr == 0) {
            JOptionPane.showMessageDialog(frame, "F critical can`t be 0", "Unknown error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        log.debug("Fcr = " + Fcr);

        double Re = 1;

        double MnIn = Re * Rpg * Fcr * Sxc;
        log.debug("Mn (in-kips) =" + MnIn);
        double MnFt = MnIn / 12;
        log.debug("Mn (ft-kips) =" + MnFt);

        // 0.9MnFt
        double MnFtpart = 0.9 * MnFt;

        if (MnFtpart > Muadj) {
            JOptionPane.showMessageDialog(frame, "Success: The section is safe", "Finished", JOptionPane.INFORMATION_MESSAGE);
            valueTW.setText(String.valueOf(trytw));
            valueHw.setText(String.valueOf(HW));
            selectedSteelValue.setText("\t\t" + getSteel(valueTW.getText(), valueHw.getText(), valueFinalTF.getText(), valueFinalBF.getText()));
            draw.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(frame, "The section FAILED!\nPlease enter another bf & tf values.", "Finished", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isEquation(String tryTW) {
        return tryTW.contains("/");
    }


    private double getValue(JTextField component, String name) throws Exception {
        double h;
        try {
            h = Double.parseDouble(component.getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Parameter " + name + " : " + nfe.getLocalizedMessage(), "Wrong parameter!", JOptionPane.ERROR_MESSAGE);
            throw new Exception("End Calculation!");
        }
        return h;
    }

    private JPanel getInputPanel() {
        JPanel input = new JPanel(new FlowLayout());
        input.setPreferredSize(new Dimension(240, 500));
        JLabel title = new JLabel("Design Parameter");
        title.setPreferredSize(new Dimension(220, 30));
        input.add(title);

        input.add(getName("  Fy"));
        valueFy = new JTextField();
        valueFy.setPreferredSize(new Dimension(70, 30));
        input.add(valueFy);
        input.add(getUnit("MPa"));

        input.add(getName("  L"));
        valueL = new JTextField();
        valueL.setPreferredSize(new Dimension(70, 30));
        input.add(valueL);
        input.add(getUnit("ft"));

        input.add(getName("  w(unfactored)"));
        valueW = new JTextField();
        valueW.setPreferredSize(new Dimension(70, 30));
        input.add(valueW);
        input.add(getUnit("k/ft"));

        input.add(getName("  tf"));
        valueTF = new JTextField();
        valueTF.setPreferredSize(new Dimension(70, 30));
        input.add(valueTF);
        input.add(getUnit("in"));

        return input;
    }

    private JPanel getOutputPanel() {
        JPanel output = new JPanel(new FlowLayout());
        output.setPreferredSize(new Dimension(260, 500));

        JLabel title = new JLabel("Output");
        title.setPreferredSize(new Dimension(220, 30));
        output.add(title);

        JLabel labelTF = new JLabel("  tf =");
        labelTF.setPreferredSize(new Dimension(100, 30));

        valueFinalTF = new JTextField("");
        valueFinalTF.setEnabled(false);
        valueFinalTF.setPreferredSize(new Dimension(70, 30));

        JLabel unitTF = new JLabel("in");
        unitTF.setPreferredSize(new Dimension(50, 30));

        output.add(labelTF);
        output.add(valueFinalTF);
        output.add(unitTF);

        JLabel labelBF = new JLabel("  bf =");
        labelBF.setPreferredSize(new Dimension(100, 30));

        valueFinalBF = new JTextField("");
        valueFinalBF.setEnabled(false);
        valueFinalBF.setPreferredSize(new Dimension(70, 30));

        JLabel unitBF = new JLabel("in");
        unitBF.setPreferredSize(new Dimension(50, 30));

        output.add(labelBF);
        output.add(valueFinalBF);
        output.add(unitBF);

        JLabel labelTW = new JLabel("  tw =");
        labelTW.setPreferredSize(new Dimension(100, 30));

        valueTW.setPreferredSize(new Dimension(70, 30));

        JLabel unitTW = new JLabel("in");
        unitTW.setPreferredSize(new Dimension(50, 30));

        output.add(labelTW);
        output.add(valueTW);
        output.add(unitTW);

        JLabel labelH = new JLabel("  H =");
        labelH.setPreferredSize(new Dimension(100, 30));


        valueH.setPreferredSize(new Dimension(70, 30));

        JLabel unitH = new JLabel("in");
        unitH.setPreferredSize(new Dimension(50, 30));

        output.add(labelH);
        output.add(valueH);
        output.add(unitH);

        JLabel labelHw = new JLabel("  hw =");
        labelHw.setPreferredSize(new Dimension(100, 30));

        valueHw.setPreferredSize(new Dimension(70, 30));

        JLabel unitHw = new JLabel("in");
        unitHw.setPreferredSize(new Dimension(50, 30));

        output.add(labelHw);
        output.add(valueHw);
        output.add(unitHw);

        /**
         * The selected stel section!
         * int[] fraction = toFractionPos(new BigDecimal("0.4375"));
         System.out.println(fraction[0] + "/" + fraction[1]); // 1/10000
         */

        JLabel selectedSteelLabel = new JLabel("  The selected steel section is: ");
        selectedSteelLabel.setPreferredSize(new Dimension(250, 30));

        selectedSteelValue.setPreferredSize(new Dimension(250, 30));

        output.add(selectedSteelLabel);
        output.add(selectedSteelValue);


        return output;
    }

    private String getSteel(String tw, String hw, String tf, String bf) {

        int[] fraction = MathUtils.toFractionPos(new BigDecimal(tw));
        String twFraction = fraction[0] + "/" + fraction[1];

        fraction = MathUtils.toFractionPos(new BigDecimal(tf));
        String tfFraction = fraction[0] + "/" + fraction[1];

        //2/5x62web & 11/2x8flanges,
        return twFraction + "x" + hw + "web and " + tfFraction + "x" + bf + "flanges";
    }

    private JLabel getName(String name) {
        JLabel labelName = new JLabel(name);
        labelName.setPreferredSize(new Dimension(100, 30));
        return labelName;
    }

    private JLabel getUnit(String unit) {
        JLabel labelUnit = new JLabel(unit);
        labelUnit.setPreferredSize(new Dimension(30, 30));
        return labelUnit;
    }


    private double getRound(double value) {

        double t = value % 0.05;

        if (t < 0.05)
            t = 0.05;
        else t = 0.1;

        return (value - (value % 0.05) + t);
    }

    public boolean isDraw() {
        return isDraw;
    }

    public double getH() {
        return H;
    }

    public double getHw() {
        return HW;
    }

    public double getBf() {
        return finalBf;
    }

    public double getTf() {
        return finalTf;
    }

    public double getTw() {
        return trytw;
    }

    public double getC() {
        return C;
    }

    public PropertyDialog getPropertyDialog() {
        return propertyDialog;
    }
}
