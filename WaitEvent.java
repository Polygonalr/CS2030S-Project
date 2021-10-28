package cs2030.simulator;

import java.util.Optional;

class WaitEvent extends Event {
    private final Server server;
    private static final int WAIT_PRIORITY = 3;

    WaitEvent(double time, Customer customer, ServerList serverList, Server server) {
        super(time, customer, serverList, WAIT_PRIORITY);
        this.server = server;
    }

    public Optional<Event> execute() {
        double time = this.server.getDoneTimeOf(
                super.getCustomer()) - super.getCustomer().getServeTime();
        return Optional.<Event>of(
            new ServeEvent(time, super.getCustomer(), super.getServerList(), server)
        );
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("%d waits at server %d",
                super.getCustomer().getId(), this.server.getId()));
    }
}