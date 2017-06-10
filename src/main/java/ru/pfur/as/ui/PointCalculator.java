package ru.pfur.as.ui;

import javafx.geometry.Point2D;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PointCalculator {

    public static Map<String, Point> points = new HashMap<String, Point>();
    public static Map<String, Point2D> points2D = new HashMap<String, Point2D>();

    public static void calculate(int x0, int y0, int bound, int width, int height, int drawtw, int drawtf) {
        Point p1 = new Point(x0 - width / 2 + bound, y0 - height / 2 + bound);
        Point p2 = new Point(x0 - width / 2 + bound, y0 - (height / 2 - drawtf) + bound);
//                    -100, 0 + 50 + drawtf);
        Point p3 = new Point(x0 - drawtw / 2, y0 - (height / 2 - drawtf) + bound);
        Point p4 = new Point(x0 - drawtw / 2, y0 + (height / 2 - drawtf) - bound);
        Point p5 = new Point(x0 - width / 2 + bound, y0 + (height / 2 - drawtf) - bound);
        Point p6 = new Point(x0 - width / 2 + bound, y0 + height / 2 - bound);

        Point p61 = new Point(x0 + width / 2 - bound, y0 + height / 2 - bound);
        Point p51 = new Point(x0 + width / 2 - bound, y0 + (height / 2 - drawtf) - bound);
        Point p41 = new Point(x0 + drawtw / 2, y0 + (height / 2 - drawtf) - bound);

        Point p31 = new Point(x0 + drawtw / 2, y0 - (height / 2 - drawtf) + bound);
        Point p21 = new Point(x0 + width / 2 - bound, y0 - (height / 2 - drawtf) + bound);
        Point p11 = new Point(x0 + width / 2 - bound, y0 - height / 2 + bound);

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
    }


    public static void calculate2D(double x0, double y0, double bound, double width, double height, double drawtw, double drawtf) {
        Point2D p1 = new Point2D(x0 - width / 2 + bound, y0 - height / 2 + bound);
        Point2D p2 = new Point2D(x0 - width / 2 + bound, y0 - (height / 2 - drawtf) + bound);
//                    -100, 0 + 50 + drawtf);
        Point2D p3 = new Point2D(x0 - drawtw / 2, y0 - (height / 2 - drawtf) + bound);
        Point2D p4 = new Point2D(x0 - drawtw / 2, y0 + (height / 2 - drawtf) - bound);
        Point2D p5 = new Point2D(x0 - width / 2 + bound, y0 + (height / 2 - drawtf) - bound);
        Point2D p6 = new Point2D(x0 - width / 2 + bound, y0 + height / 2 - bound);

        Point2D p61 = new Point2D(x0 + width / 2 - bound, y0 + height / 2 - bound);
        Point2D p51 = new Point2D(x0 + width / 2 - bound, y0 + (height / 2 - drawtf) - bound);
        Point2D p41 = new Point2D(x0 + drawtw / 2, y0 + (height / 2 - drawtf) - bound);

        Point2D p31 = new Point2D(x0 + drawtw / 2, y0 - (height / 2 - drawtf) + bound);
        Point2D p21 = new Point2D(x0 + width / 2 - bound, y0 - (height / 2 - drawtf) + bound);
        Point2D p11 = new Point2D(x0 + width / 2 - bound, y0 - height / 2 + bound);

        points2D.put("p1", p1);
        points2D.put("p2", p2);
        points2D.put("p3", p3);
        points2D.put("p4", p4);
        points2D.put("p5", p5);
        points2D.put("p6", p6);
        points2D.put("p61", p61);
        points2D.put("p51", p51);
        points2D.put("p41", p41);
        points2D.put("p31", p31);
        points2D.put("p21", p21);
        points2D.put("p11", p11);
    }
}
