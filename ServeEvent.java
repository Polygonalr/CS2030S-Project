package cs2030.simulator;

import java.util.Optional;
import java.util.LinkedList;

class ServeEvent extends Event {
    private final Server server;
    private static final int SERVE_PRIORITY = 2;

    ServeEvent(double time, Customer customer, ServerList serverList, Server server) {
        super(time, customer, serverList, SERVE_PRIORITY, true, false);
        this.server = server;
        serverList.setUnavailable(server);
        serverList.setNextAvailableTime(server, time);
    }

    public Optional<Event> execute() {
        return Optional.of(new DoneEvent(this.getDoneTime(), super.getCustomer(),
                    super.getServerList(), server));
    }

    private double getDoneTime() {
        return super.getCustomer().getServeTime() + super.getTime();
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("serves by %s", this.server.toString()));
    }
}