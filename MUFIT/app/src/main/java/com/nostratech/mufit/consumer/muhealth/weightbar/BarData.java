package com.nostratech.mufit.consumer.muhealth.weightbar;

public class BarData {

    private final int color;
    private final double cutoff;
    private final String label;

    public BarData(int color, double cutoff, String label) {
        this.color = color;
        this.cutoff = cutoff;
        this.label = label;
    }

    public int getColor() {
        return color;
    }

    public double getCutoff() {
        return cutoff;
    }

    public String getLabel() {
        return label;
    }
}
