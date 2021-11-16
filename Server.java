package cs2030.simulator;

import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

class Server implements Comparable<Server> {
    private final int id;
    private final LinkedList<Map.Entry<Customer, Double>> queue; // double is donetime
    private final Optional<Map.Entry<Customer, Double>> currentCustomer;
    private final int maxQueueLength;
    private final boolean available;
    private final double nextAvailableTime;

    Server(int id, int maxQueueLength) {
        this.id = id;
        this.queue = new LinkedList<Map.Entry<Customer, Double>>();
        this.currentCustomer = Optional.empty(); 
        this.maxQueueLength = maxQueueLength;
        this.available = true;
        this.nextAvailableTime = 0.0;
    }

    // Literally only for SelfCheckout, hacky way to make sure they share the same queue
    protected Server(int id, int maxQueueLength, LinkedList<Map.Entry<Customer, Double>> queue) {
        this.id = id;
        this.queue = queue;
        this.currentCustomer = Optional.empty(); 
        this.maxQueueLength = maxQueueLength;
        this.available = true;
        this.nextAvailableTime = 0.0;
    }

    Server(Server s) {
        this.id = s.id;
        this.queue = s.queue;
        this.currentCustomer = s.currentCustomer;
        this.maxQueueLength = s.maxQueueLength;
        this.available = s.available;
        this.nextAvailableTime = s.nextAvailableTime;
    }

    Server(Server s, boolean available) {
        this.id = s.id;
        this.queue = s.queue;
        this.currentCustomer = s.currentCustomer;
        this.maxQueueLength = s.maxQueueLength;
        this.available = available;
        this.nextAvailableTime = s.nextAvailableTime;
    }

    Server(Server s, double nextAvailableTime) {
        this.id = s.id;
        this.queue = s.queue;
        this.currentCustomer = s.currentCustomer;
        this.maxQueueLength = s.maxQueueLength;
        this.available = s.available;
        this.nextAvailableTime = nextAvailableTime;
    }

    Server(Server s, Optional<Map.Entry<Customer, Double>> currentCustomer) {
        this.id = s.id;
        this.queue = s.queue;
        this.currentCustomer = currentCustomer;
        this.maxQueueLength = s.maxQueueLength;
        this.available = s.available;
        this.nextAvailableTime = s.nextAvailableTime;
    }

    public int getId() {
        return this.id;
    }

    public Optional<Map.Entry<Customer, Double>> getCurrentCustomer() {
        return this.currentCustomer;
    }

    public double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    public double getDoneTimeOf(Customer customer) {
        return queue.stream().filter(x -> x.getKey().equals(customer)).findFirst()
                .<Double>map(x -> x.getValue()).orElse(0.0);
    }

    public Server serveNext() {
        Optional<Map.Entry<Customer, Double>> nextCustomer = Optional.ofNullable(this.queue.poll());
        return new Server(this, nextCustomer);
    }

    public Server addCustomerToQueue(Map.Entry<Customer, Double> newCustomer)
            throws ArrayIndexOutOfBoundsException {
        this.currentCustomer.ifPresent​(
            unused -> {
                if (this.queue.size() >= this.maxQueueLength) {
                throw new ArrayIndexOutOfBoundsException(String.format("Error adding "
                        + "Customer to a full queue for server %d", this.id));
                }
                this.queue.addLast(newCustomer);
            }
        );
        Optional<Map.Entry<Customer, Double>> newCurrentCustomer = Optional.of(this.currentCustomer
                .orElse(newCustomer));
        return new Server(this, newCurrentCustomer);
    }

    // To be overridden in Selfcheckout
    public Server rest(double currentTime, LinkedList<Double> restTimes) {
        double restUntil = currentTime + restTimes.poll();
        return this.setNextAvailableTime(restUntil).setUnavailable();
    }

    public boolean hasQueueSlot() {
        return this.queue.size() < this.maxQueueLength;
    }

    public int getQueueLength() {
        return this.queue.size();
    }

    public boolean isServing() {
        return this.currentCustomer.map(x -> true).orElse(false);
    }

    public Server setAvailable() {
        return new Server(this, true);
    }

    public Server setUnavailable() {
        return new Server(this, false);
    }

    public boolean isAvailable() {
        return this.available;
    }

    public Server setNextAvailableTime(double time) {
        return new Server(this, time);
    }

    public String debugPrint() {
        String toReturn = String.format("Server %d: %s [",  this.id, this.currentCustomer.map(x -> x.getKey().toString()).orElse("None"));
        for (int i = 0; i < this.queue.size(); i++) {
            toReturn += queue.get(i).getKey().toString() + ", ";
        }
        return toReturn + "] " + (this.available ? "avail" : "unavail") + "\n";
    }

    public int compareTo(Server s) {
        return this.id - s.id;
    }

    public boolean equals(Object o) {
        if (o instanceof Server) {
            return this.id == ((Server) o).id;
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return String.format("server %d", this.id);
    }
}