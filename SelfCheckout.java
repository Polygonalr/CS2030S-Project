package cs2030.simulator;

import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

class SelfCheckout extends Server {
    SelfCheckout(int id, int maxQueueLength, LinkedList<Map.Entry<Customer, Double>> queue) {
        super(id, maxQueueLength, queue);
    }

    // -- Copy and paste methods from Server bcos idk how to make it return SelfCheckout instead --

    SelfCheckout(Server s) {
        super(s);
    }

    @Override
    public SelfCheckout addCustomerToQueue(Map.Entry<Customer, Double> newCustomer)
            throws ArrayIndexOutOfBoundsException {
        return new SelfCheckout(super.addCustomerToQueue(newCustomer));
    }

    public SelfCheckout setAvailable() {
        return new SelfCheckout(super.setAvailable());
    }

    public SelfCheckout setUnavailable() {
        return new SelfCheckout(super.setUnavailable());
    }

    public SelfCheckout setNextAvailableTime(double time) {
        return new SelfCheckout(super.setNextAvailableTime(time));
    }

    public SelfCheckout serveNext() {
        return new SelfCheckout(super.serveNext());
    }

    // --------------------------------------------------------------------------------------------
    @Override
    public Server rest(double currentTime, LinkedList<Double> restTimes) {
        // double restUntil = currentTime + restTimes.poll();
        return this.setNextAvailableTime(currentTime).setUnavailable();
        // restTimes.poll();
        // return this;
    }

    @Override
    public String debugPrint() {
        return super.debugPrint().replace("Server", "self-check");
    }

    @Override
    public String toString() {
        return super.toString().replace("server", "self-check");
    }
}