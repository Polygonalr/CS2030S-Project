package cs2030.simulator;

class Statistics {
    private final double[] statistics;
    private static final int I_TOTAL_WAIT = 0;
    private static final int I_CUSTOMERS_SERVED = 1;
    private static final int I_CUSTOMERS_LEFT = 2;

    Statistics() {
        this.statistics = new double[] {0, 0, 0};
    }

    public void countServedCustomer() {
        this.statistics[I_CUSTOMERS_SERVED]++;
    }

    public void countLeftCustomer() {
        this.statistics[I_CUSTOMERS_LEFT]++;
    }

    public void addWaitTime(double time) {
        this.statistics[I_TOTAL_WAIT] += time;
    }

    public String toString() {
        return String.format("[%.3f %d %d]",
                this.statistics[I_TOTAL_WAIT] / this.statistics[I_CUSTOMERS_SERVED],
                (int) this.statistics[I_CUSTOMERS_SERVED],
                (int) this.statistics[I_CUSTOMERS_LEFT]);
    }
}