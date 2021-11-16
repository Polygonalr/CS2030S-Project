package cs2030.simulator;

import java.util.Optional;
import java.util.function.Supplier;

class Customer {
    private final int id;
    private final double arrivalTime;
    private final Supplier<Double> serveTime;
    private Optional<Double> serveTimeCache;
    private final boolean greedy;

    Customer(int id, double arrivalTime, double serveTime, boolean greedy) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serveTimeCache = Optional.<Double>of(serveTime);
        this.serveTime = () -> 0.0;
        this.greedy = greedy;
    }

    Customer(int id, double arrivalTime, double serveTime) {
        this(id, arrivalTime, serveTime, false);
    }

    Customer(int id, double arrivalTime, Supplier<Double> serveTime, boolean greedy) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serveTimeCache = Optional.empty();
        this.serveTime = serveTime;
        this.greedy = greedy;
    }

    public int getId() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getServeTime() {
        double finalServeTime = this.serveTimeCache.orElseGet(this.serveTime);
        this.serveTimeCache = Optional.<Double>of(finalServeTime);
        return finalServeTime;
    }

    public boolean isGreedy() {
        return this.greedy;
    }

    public String toString() {
        return String.format("%d%s", this.id, (this.greedy ? "(greedy)" : ""));
    }
}