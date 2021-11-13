package cs2030.simulator;

class Customer {
    private final int id;
    private final double arrivalTime;
    private final double serveTime;

    Customer(int id, double arrivalTime, double serveTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serveTime = serveTime;
    }

    public int getId() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getServeTime() {
        return this.serveTime;
    }

    public String toString() {
        return String.format("Customer %d", this.id);
    }
}