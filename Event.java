package cs2030.simulator;

import java.util.Optional;
import java.util.Map;
import java.util.LinkedList;

abstract class Event {
    private final int priority; // for EventComparator
    private final double time;
    private final Customer customer;
    private final ServerList serverList;
    // whether to add to the final Event List to print, for recursive WaitEvents and RestEvents
    private final boolean addToFinal;
    // I hate cyclic dependency limitation.
    private final boolean isRest;

    Event(double time, Customer customer, ServerList serverList, int priority, boolean addToFinal,
            boolean isRest) {
        this.time = time;
        this.customer = customer;
        this.serverList = serverList;
        this.priority = priority;
        this.addToFinal = addToFinal;
        this.isRest = isRest;
    }

    Event(Customer customer, ServerList serverList, int priority) {
        this(customer.getArrivalTime(), customer, serverList, priority, true, false);
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

    public boolean isRestEvent() {
        return this.isRest;
    }

    String descriptivePrint(String s) {
        return String.format("%.3f %s", this.time, s);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time);
    }

    // To be overriden in RestEvent
    public Optional<Map.Entry<Customer, Double>> dequeue() {
        return Optional.empty();
    }

    // To be overriden in RestEvent
    public Optional<Server> freedServer() {
        return Optional.empty();
    }

    public abstract Optional<Event> execute() throws Exception;
}