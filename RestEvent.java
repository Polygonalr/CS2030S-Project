package cs2030.simulator;

import java.util.Optional;
import java.util.LinkedList;

class RestEvent extends Event {
    private final Server server;
    private static final int REST_PRIORITY = 3;

    RestEvent(double time, Customer customer, ServerList serverList, Server server) {
        super(time, customer, serverList, REST_PRIORITY, false);
        this.server = server;
        serverList.setUnavailable(server);
        serverList.setNextAvailableTime(server, time);
    }

    public Optional<Event> execute() {
        this.getServerList().setAvailable(this.server);
        this.getServerList().serveNext(this.server);
        return Optional.empty();
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("server %d resting",
                this.server.getId()));
    }
}