package cs2030.simulator;

import java.util.Optional;
import java.util.LinkedList;

class RestEvent extends Event {
    private final Server server;
    private static final int REST_PRIORITY = 5;

    RestEvent(double time, Customer customer, ServerList serverList, Server server,
            LinkedList<Double> restTimes) {
        super(time, customer, serverList, REST_PRIORITY, restTimes);
        this.server = server;
    }

    public Optional<Event> execute() {
        this.server.serveNext();
        return Optional.empty();
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("server %d resting",
                this.server.getId()));
    }
}