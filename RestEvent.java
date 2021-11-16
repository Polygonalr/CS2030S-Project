package cs2030.simulator;

import java.util.Optional;
import java.util.Map;
import java.util.LinkedList;

class RestEvent extends Event {
    private final Server server;
    private static final int REST_PRIORITY = 3;

    RestEvent(double time, Customer customer, ServerList serverList, Server server) {
        super(time, customer, serverList, REST_PRIORITY, false, true);
        this.server = server;
        serverList.setUnavailable(server);
        serverList.setNextAvailableTime(server, time);
    }

    public Optional<Event> execute() {
        // this.getServerList().setAvailable(this.server);
        // this.getServerList().serveNext(this.server);
        // return Optional.empty();
        // this.getServerList().setAvailable(this.server);
        // Optional<Map.Entry<Customer, Double>> nextCustomer =
        //         this.getServerList().serveNext(this.server);
        // Optional<Event> e = nextCustomer.<Event>map(cust -> 
        //     new ServeEvent(
        //         RestEvent.super.getTime(),
        //         cust.getKey(),
        //         RestEvent.super.getServerList(),
        //         RestEvent.this.server
        //     )
        // );
        // return e;
        return Optional.empty();
    }

    public Optional<Map.Entry<Customer, Double>> dequeue() {
        return this.getServerList().serveNext(this.server);
    }

    // To be overriden in RestEvent
    public Optional<Server> freedServer() {
        return Optional.of(this.server);
    }

    @Override
    public String toString() {
        return super.descriptivePrint(String.format("server %d resting",
                this.server.getId()));
    }
}