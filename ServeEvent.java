package cs2030.simulator;

import java.util.Optional;

class ServeEvent extends Event {
    private final Server server;
    private static final int SERVE_PRIORITY = 2;

    ServeEvent(double time, Customer customer, ServerList serverList, Server server) {
        super(time, customer, serverList, SERVE_PRIORITY);
        this.server = server;
    }

    public Optional<Event> execute() {
        return Optional.of(new DoneEvent(this.getDoneTime(), super.getCustomer(),
                    super.getServerList(), server));
    }

    public double getDoneTime() {
        return super.getCustomer().getServeTime() + super.getTime();
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("%d serves by server %d",
                super.getCustomer().getId(), this.server.getId()));
    }
}