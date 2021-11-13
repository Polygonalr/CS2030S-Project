package cs2030.simulator;

import java.util.Optional;
import java.util.LinkedList;

class DoneEvent extends Event {
    private final Server server;
    private static final int DONE_PRIORITY = 4;

    DoneEvent(double time, Customer customer, ServerList serverList, Server server,
            LinkedList<Double> restTimes) {
        super(time, customer, serverList, DONE_PRIORITY, restTimes);
        this.server = server;
    }

    public Optional<Event> execute() {
        return Optional.of(new RestEvent(super.getTime() + this.getRestTimes().poll(),
                this.getCustomer(), this.getServerList(), this.server, super.getRestTimes()));
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("%d done serving by server %d",
                super.getCustomer().getId(), this.server.getId()));
    }
}