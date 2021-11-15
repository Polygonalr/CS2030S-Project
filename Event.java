package cs2030.simulator;

import java.util.Optional;
import java.util.LinkedList;

abstract class Event {
    private final int priority; // for EventComparator
    private final double time;
    private final Customer customer;
    private final ServerList serverList;
    private final LinkedList<Double> restTimes;
    // whether to add to the final Event List to print, for recursive WaitEvents and RestEvents
    private final boolean addToFinal;

    Event(double time, Customer customer, ServerList serverList, int priority,
            LinkedList<Double> restTimes, boolean addToFinal) {
        this.time = time;
        this.customer = customer;
        this.serverList = serverList;
        this.priority = priority;
        this.restTimes = restTimes;
        this.addToFinal = addToFinal;
    }

    Event(Customer customer, ServerList serverList, int priority, LinkedList<Double> restTimes) {
        this(customer.getArrivalTime(), customer, serverList, priority, restTimes, true);
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

    public boolean toAddToFinal() {
        return this.addToFinal;
    }

    public LinkedList<Double> getRestTimes() {
        return this.restTimes;
    }

    String descriptivePrint(String s) {
        return String.format("%.3f %s", this.time, s);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time);
    }

    public abstract Optional<Event> execute() throws Exception;
}