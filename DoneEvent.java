package cs2030.simulator;

import java.util.Optional;

class DoneEvent extends Event {
    private final Server server;
    private static final int DONE_PRIORITY = 4;

    DoneEvent(double time, Customer customer, ServerList serverList, Server server) {
        super(time, customer, serverList, DONE_PRIORITY);
        this.server = server;
    }

    public Optional<Event> execute() {
        this.server.serveNext();
        return Optional.empty();
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("%d done serving by server %d",
                super.getCustomer().getId(), this.server.getId()));
    }
}