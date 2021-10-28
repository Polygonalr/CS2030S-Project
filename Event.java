package cs2030.simulator;

import java.util.Optional;

abstract class Event {
    private final int priority; // for EventComparator
    private final double time;
    private final Customer customer;
    private final ServerList serverList;

    Event(double time, Customer customer, ServerList serverList, int priority) {
        this.time = time;
        this.customer = customer;
        this.serverList = serverList;
        this.priority = priority;
    }

    Event(Customer customer, ServerList serverList, int priority) {
        this(customer.getArrivalTime(), customer, serverList, priority);
    }

    public double getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public ServerList getServerList() {
        return this.serverList;
    }

    public int getPriority() {
        return this.priority;
    }

    String descriptivePrint(String s) {
        return String.format("%.3f %s", this.time, s);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time);
    }

    public abstract Optional<Event> execute();
}