package cs2030.simulator;

import java.util.Optional;
import java.util.LinkedList;

class WaitEvent extends Event {
    private final Server server;
    private final Statistics statistics;
    private static final int WAIT_PRIORITY = 5;

    WaitEvent(double time, Customer customer, ServerList serverList, Server server,
            LinkedList<Double> restTimes, Statistics statistics) {
        super(time, customer, serverList, WAIT_PRIORITY, restTimes, true);
        this.server = server;
        this.statistics = statistics;
    }

    WaitEvent(WaitEvent w, double time) {
        super(time, w.getCustomer(), w.getServerList(), WAIT_PRIORITY, w.getRestTimes(), false);
        this.server = w.server;
        this.statistics = w.statistics;
    }

    public Optional<Event> execute() throws Exception {
        double serverNextAvailableTime = this.getServerList().getNextAvailableTime(this.server);
        if (serverNextAvailableTime == 0.0) {
            throw new Exception("Something went wrong, nextAvailableTime not updated correctly");
        }
        this.statistics.addWaitTime(serverNextAvailableTime - this.getTime());
        if (this.getServerList().isAvailable(this.server)) {
            return Optional.<Event>of(
                new ServeEvent(serverNextAvailableTime, super.getCustomer(),
                        super.getServerList(), server, this.getRestTimes())
            );
        }
        return Optional.<Event>of(
            new WaitEvent(this, serverNextAvailableTime)
        );
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("%d waits at server %d",
                super.getCustomer().getId(), this.server.getId()));
    }
}