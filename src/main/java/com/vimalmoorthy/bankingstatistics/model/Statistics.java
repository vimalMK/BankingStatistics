package com.vimalmoorthy.bankingstatistics.model;

import java.lang.Math;
import lombok.Data;

/**
 * Entities for Statistics such as sum,avg,maximum and minimum transaction are represented.
 * All the statistic values which changes during operation are handled.
 * Created by Vimal on 04/17/2018.
 */
@Data
public class Statistics {
    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;

    public Statistics() {}

    /**
     * Statistics constructor for assigning object
     */
    public Statistics(Statistics recuur) {
        this.sum = recuur.getSum();
        this.avg = recuur.getAvg();
        this.max = recuur.getMax();
        this.min = recuur.getMin();
        this.count = recuur.getCount();
    }

    /**
     *All the getter ans setter methods for sum,min,max,avg and count.
     */
    public double getSum() {
        return sum;
    }

    public double getAvg() {
        return avg;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setCount(long count) {
        this.count = count;
    }

    /**
     * addNewAmount method adds new transaction to the statistics.
     * the count is incremented for every transaction
     * sum is added up with the current transaction amount
     * respective Average value is calculated.
     * new maximum ans minimum transaction value is updated to the statistics
     */
    public void addNewAmount(double amount) {
        long newCount = getCount() + 1;
        double newSum = getSum() + amount;
        double newMax = Math.max(getMax(), amount);
        double newMin = (getMin() == 0) ? amount : Math.min(getMin(), amount);
        double newAvg = getAvg() + ((amount - getAvg()) / newCount);
        setMax(newMax);
        setMin(newMin);
        setSum(newSum);
        setCount(newCount);
        setAvg(newAvg);
    }

    @Override
    public String toString() {
        return "Statistic {sum: " + sum +
                ", avg: " + avg +
                ", max: " + max +
                ", min: " + min +
                ", count: " + count + "}";
    }
}