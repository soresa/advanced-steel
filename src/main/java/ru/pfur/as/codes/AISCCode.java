package ru.pfur.as.codes;

public class AISCCode {
    double tw;
    double twGreater;
    double bf;
    private String shape;

    public AISCCode(String shape, double tw, double twGreater, double bf) {
        this.shape = shape;
        this.tw = tw;
        this.twGreater = twGreater;
        this.bf = bf;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public double getTw() {
        return tw;
    }

    public void setTw(double tw) {
        this.tw = tw;
    }

    public double getTwGreater() {
        return twGreater;
    }

    public void setTwGreater(double twGreater) {
        this.twGreater = twGreater;
    }

    public double getBf() {
        return bf;
    }

    public void setBf(double bf) {
        this.bf = bf;
    }

    @Override
    public String toString() {
        return "AISCCode{" +
                "shape='" + shape + '\'' +
                ", tw=" + tw +
                ", twGreater=" + twGreater +
                ", bf=" + bf +
                '}';
    }
}
