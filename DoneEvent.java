package cs2030.simulator;

import java.util.Optional;
import java.util.LinkedList;

class DoneEvent extends Event {
    private final Server server;
    private static final int DONE_PRIORITY = 4;

    DoneEvent(double time, Customer customer, ServerList serverList, Server server) {
        super(time, customer, serverList, DONE_PRIORITY, true);
        this.server = server;
        serverList.setUnavailable(server);
        serverList.setNextAvailableTime(server, time);
    }

    public Optional<Event> execute() {
        double nextAvailableTime = this.getServerList().rest(server, super.getTime());
        return Optional.of(new RestEvent(nextAvailableTime, this.getCustomer(),
                this.getServerList(), this.server));
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("%d done serving by %s",
                super.getCustomer().getId(), this.server.toString()));
    }
}