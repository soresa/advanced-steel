package ru.pfur.as.ui;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DrawPanel extends JPanel {

    final static float dash1[] = {10.0f};
    final static BasicStroke dashed =
            new BasicStroke(1.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);
    final static BasicStroke solid =
            new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    DrawInterface drawInterface;
    Color axisColor = new Color(0f, 0f, 0f, 0.5f);
    Color main = new Color(0f, 0f, 0f, 0.8f); //rgb = red, green, blue + alpha
    Color dimensionMain = new Color(0.05f, 0.05f, 0.05f, 0.5f); //rgb = red, green, blue + alpha
    private Map<String, Point> points = new HashMap<String, Point>();
    private int dinc = 25;
    private int xd = 5;
    private int tx = 20;

    public DrawPanel(DrawInterface drawInterface) {
        this.drawInterface = drawInterface;
        this.setLayout(new FlowLayout());
    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        this.setBackground(PropertyDialog.back);

        Graphics2D graph = (Graphics2D) graphics;
        //Try 3D

        if (drawInterface.isDraw()) {

            log.debug("width = " + this.getWidth());
            log.debug("height = " + this.getHeight());
            log.debug("tf = " + drawInterface.getTf());
            log.debug("tw = " + drawInterface.getTw());

            int x0 = this.getWidth() / 2;
            int y0 = this.getHeight() / 2;

            graph.setStroke(dashed);
            graph.setColor(axisColor);

            graph.drawLine(0, y0, this.getWidth(), y0);
            graph.drawLine(x0, 0, x0, this.getHeight());

            graph.setColor(PropertyDialog.lineColor);
            graph.setStroke(solid);

            int bound = 50;

            double h = drawInterface.getH();
            double tf = drawInterface.getTf();
            double bf = drawInterface.getBf();
            double tw = drawInterface.getTw();
            double hw = drawInterface.getHw();


            int drawtf = (int) (tf * 30);
            int drawbf = (int) (bf * 30);
            int drawtw = (int) (tw * 50);
            if (drawtw == 0)
                drawtw = drawtf / 3;
            int drawhw = (int) (hw * 30);
            int drawh = (int) (h * 30);


            // find points
            Point p1 = new Point(x0 - getWidth() / 2 + bound, y0 - getHeight() / 2 + bound);
            Point p2 = new Point(x0 - getWidth() / 2 + bound, y0 - (getHeight() / 2 - drawtf) + bound);
//                    -100, 0 + 50 + drawtf);
            Point p3 = new Point(x0 - drawtw / 2, y0 - (getHeight() / 2 - drawtf) + bound);
            Point p4 = new Point(x0 - drawtw / 2, y0 + (getHeight() / 2 - drawtf) - bound);
            Point p5 = new Point(x0 - getWidth() / 2 + bound, y0 + (getHeight() / 2 - drawtf) - bound);
            Point p6 = new Point(x0 - getWidth() / 2 + bound, y0 + getHeight() / 2 - bound);

            Point p61 = new Point(x0 + getWidth() / 2 - bound, y0 + getHeight() / 2 - bound);
            Point p51 = new Point(x0 + getWidth() / 2 - bound, y0 + (getHeight() / 2 - drawtf) - bound);
            Point p41 = new Point(x0 + drawtw / 2, y0 + (getHeight() / 2 - drawtf) - bound);

            Point p31 = new Point(x0 + drawtw / 2, y0 - (getHeight() / 2 - drawtf) + bound);
            Point p21 = new Point(x0 + getWidth() / 2 - bound, y0 - (getHeight() / 2 - drawtf) + bound);
            Point p11 = new Point(x0 + getWidth() / 2 - bound, y0 - getHeight() / 2 + bound);

            points.put("p1", p1);
            points.put("p2", p2);
            points.put("p3", p3);
            points.put("p4", p4);
            points.put("p5", p5);
            points.put("p6", p6);
            points.put("p61", p61);
            points.put("p51", p51);
            points.put("p41", p41);
            points.put("p31", p31);
            points.put("p21", p21);
            points.put("p11", p11);


            graph.setStroke(new BasicStroke(PropertyDialog.thickness));

            // connect points
            graph.drawLine(p1.x, p1.y, p2.x, p2.y);
            graph.drawLine(p2.x, p2.y, p3.x, p3.y);
            graph.drawLine(p3.x, p3.y, p4.x, p4.y);
            graph.drawLine(p4.x, p4.y, p5.x, p5.y);
            graph.drawLine(p5.x, p5.y, p6.x, p6.y);
            graph.drawLine(p6.x, p6.y, p61.x, p61.y);
            graph.drawLine(p61.x, p61.y, p51.x, p51.y);
            graph.drawLine(p51.x, p51.y, p41.x, p41.y);
            graph.drawLine(p41.x, p41.y, p31.x, p31.y);
            graph.drawLine(p31.x, p31.y, p21.x, p21.y);
            graph.drawLine(p21.x, p21.y, p11.x, p11.y);
            graph.drawLine(p11.x, p11.y, p1.x, p1.y);

            if (true) {
                drawDimensions(graph);
            }


        }

    }

    private void drawDimensions(Graphics2D graph) {
        graph.setStroke(new BasicStroke(1.0f));
        graph.setColor(dimensionMain);

        // start draw tf

        graph.drawLine(points.get("p1").x, points.get("p1").y, points.get("p1").x - dinc, points.get("p1").y);
        graph.drawLine(points.get("p2").x, points.get("p2").y, points.get("p2").x - dinc, points.get("p2").y);
        graph.drawLine(points.get("p1").x - dinc + xd, points.get("p1").y - xd, points.get("p2").x - dinc + xd, points.get("p2").y + xd);

        String tf = String.valueOf(drawInterface.getTf());

        int d = (points.get("p2").y - points.get("p1").y) / 2 - 5;

        graph.drawString(tf, points.get("p1").x - dinc - tx, points.get("p2").y - d);

        // end draw tf

        // start draw tw

        graph.drawLine(points.get("p3").x, points.get("p31").y + 100, points.get("p31").x + dinc, points.get("p31").y + 100);
//        graph.drawLine(points.get("p3").x, points.get("p3").y + 100, points.get("p3").x - dinc, points.get("p3").y + 100);

        String tw = String.valueOf(drawInterface.getTw());

        graph.drawString(tw, points.get("p31").x + dinc + 2, points.get("p31").y + 100 + 5);

        // end draw tw

        // start draw hw

        graph.drawLine(points.get("p2").x + dinc * 2, points.get("p2").y, points.get("p2").x + dinc * 2, points.get("p5").y);
//        graph.drawLine(points.get("p3").x, points.get("p3").y + 100, points.get("p3").x - dinc, points.get("p3").y + 100);

        String hw = String.valueOf(drawInterface.getHw());

        int hwc = (points.get("p5").y - points.get("p2").y) / 2;

        graph.drawString(hw, points.get("p2").x + dinc * 2 + 2, points.get("p2").y + hwc);

        // end draw hw

        // start draw bf

        graph.drawLine(points.get("p6").x, points.get("p6").y, points.get("p6").x, points.get("p6").y + dinc);
        graph.drawLine(points.get("p61").x, points.get("p61").y, points.get("p61").x, points.get("p61").y + dinc);
        graph.drawLine(points.get("p6").x - xd, points.get("p6").y + dinc - xd, points.get("p61").x + xd, points.get("p61").y + dinc - xd);

        String bf = String.valueOf(drawInterface.getBf());

        int bfc = (points.get("p61").x - points.get("p6").x) / 2;

        graph.drawString(bf, points.get("p6").x + bfc, points.get("p6").y + dinc + 10);

        // end draw bf

        // start draw H

        graph.drawLine(points.get("p11").x, points.get("p11").y, points.get("p11").x + dinc, points.get("p11").y);
        graph.drawLine(points.get("p61").x, points.get("p61").y, points.get("p61").x + dinc, points.get("p61").y);
        graph.drawLine(points.get("p11").x + dinc - xd, points.get("p11").y - xd, points.get("p61").x + dinc - xd, points.get("p61").y + xd);

        String h = String.valueOf(drawInterface.getH());

        int hc = (points.get("p61").y - points.get("p11").y) / 2;

        graph.drawString(h, points.get("p11").x, points.get("p11").y + hc);

        // end draw H

    }
}
