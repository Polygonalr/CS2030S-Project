package cs2030.simulator;

import java.util.LinkedList;
import java.util.Map;

class Server implements Comparable<Server> {
    private final int id;
    private final LinkedList<Map.Entry<Customer, Double>> queue; // double is donetime
    private final int maxQueueLength;
    private final boolean available;
    private final double nextAvailableTime;
    private static final double BIG_DOUBLE = 99999.9;

    Server(int id, int maxQueueLength) {
        this.id = id;
        this.queue = new LinkedList<Map.Entry<Customer, Double>>();
        this.maxQueueLength = maxQueueLength;
        this.available = true;
        this.nextAvailableTime = 0.0;
    }

    Server(Server s, boolean available) {
        this.id = s.id;
        this.queue = s.queue;
        this.maxQueueLength = s.maxQueueLength;
        this.available = available;
        this.nextAvailableTime = s.nextAvailableTime;
    }

    Server(Server s, double nextAvailableTime) {
        this.id = s.id;
        this.queue = s.queue;
        this.maxQueueLength = s.maxQueueLength;
        this.available = s.available;
        this.nextAvailableTime = nextAvailableTime;
    }

    public int getId() {
        return this.id;
    }

    public double getNextAvailableTime() {
        // if (this.queue.size() > 0) {
        //     return this.queue.getLast().getValue().doubleValue();
        // }
        // // It should never reach this point but this is added for completioness
        // return BIG_DOUBLE;
        return this.nextAvailableTime;
    }

    public double getDoneTimeOf(Customer customer) {
        return queue.stream().filter(x -> x.getKey().equals(customer)).findFirst()
                .<Double>map(x -> x.getValue()).orElse(0.0);
    }

    public Server serveNext() {
        this.queue.removeFirst();
        return this;
    }

    public Server addCustomerToQueue(Map.Entry<Customer, Double> customer)
            throws ArrayIndexOutOfBoundsException {
        if (this.queue.size() >= this.maxQueueLength + 1) {
            throw new ArrayIndexOutOfBoundsException(String.format("Error adding Customer to a"
                    + " full queue for server %d", this.id));
        }
        this.queue.addLast(customer);
        return this;
    }

    public boolean hasQueueSlot() {
        return this.queue.size() < this.maxQueueLength + 1;
    }

    public int getQueueLength() {
        return this.queue.size();
    }

    public boolean isServing() {
        return this.queue.size() > 0;
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

    @Override
    public String toString() {
        String toReturn = String.format("Server %d: [", this.id);
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
}